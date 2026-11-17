package org.route.service;

import lombok.RequiredArgsConstructor;
import org.route.config.OrderProperties;
import org.route.exception.OrderException;
import org.route.model.Customer;
import org.route.model.Items.OrderItem;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.route.model.orderPromotion.Promotion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.route.repository.OrderInvoiceRepository;
import org.route.repository.PromotionRepository;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderInvoiceService {
    private final OrderService orderService;
    private final OrderInvoiceRepository invoiceRepository;
    private final PromotionRepository promotionRepository;
    private final OrderProperties  orderProperties;
    private final DeliveryDistanceService  deliveryDistanceService;


    public OrderInvoice createInvoice(Order order){
        return new OrderInvoice(order);
    }

    private void calculateSubTotal(Order order, OrderInvoice invoice){
        BigDecimal total =new BigDecimal("0");
        for (OrderItem item:order.getItems().values()) {
            total = total.add(orderService.calculateSubTotal(item));
        }
        invoice.setSubTotal(total);
    }

    private void calculateDeliveryFee(OrderInvoice invoice,Customer customer,Order order){
        double distance;
        try {
            distance=deliveryDistanceService.calculate(order.getRestaurant().getDistrict(),order.getDeliveryAddress().getDistrict());
        }catch (RuntimeException e){
            throw new OrderException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        distance-=orderProperties.getDeliveryFree();
        invoice.setDeliveryFee(
               orderProperties.getDeliveryBase().add(
                        orderProperties.getDeliveryFeePerKm().multiply(BigDecimal.valueOf(distance))));

        BigDecimal loyaltyDiscount=customer.getLoyaltyTier().getDeliveryFeeDiscount().
                                            multiply(invoice.getDeliveryFee());
        invoice.setDeliveryFee(invoice.getDeliveryFee().
                subtract(loyaltyDiscount));
    }

    private void calculateServiceFee(OrderInvoice invoice){
        invoice.setServiceFee(invoice.getSubTotal().multiply(orderProperties.getServiceRate()));
    }

    public boolean applyPromotion(Promotion promotion,OrderInvoice invoice,Customer customer,Order order){
        if(invoice.getPromotion()!=null)
            return false;
        return promotion.applyPromotion(invoice,customer,order);
    }

    public BigDecimal calculateTotal(OrderInvoice invoice){
        return invoice.getSubTotal().add(invoice.getDeliveryFee()).add(invoice.getServiceFee());
    }

    public void calculateInvoice(Order order, OrderInvoice invoice, Customer customer){
        calculateSubTotal(order,invoice);
        calculateDeliveryFee(invoice,customer,order);
        calculateServiceFee(invoice);

    }

    public OrderInvoice findInvoiceById(Order order){
        return invoiceRepository.findById(order).orElseThrow(()->new OrderException(HttpStatus.NOT_FOUND,"there is no invoice for this order."));
    }

    public void applyPromotion(OrderInvoice invoice, Customer customer, Order order,String promotionCode){
        if(promotionCode==null)
            return;
        Promotion promotion=promotionRepository.getPromotion(promotionCode.toLowerCase()).orElseThrow(()->new OrderException(HttpStatus.NOT_FOUND,"invalid promotion code"));
        if(applyPromotion(promotion,invoice,customer,order)){
            invoice.setPromotion(promotion);
        }
    }

    public void setInvoice(Order order,Customer customer,String promotionCode){
        OrderInvoice invoice=createInvoice(order);
        calculateInvoice(order,invoice,customer);
        invoiceRepository.save(invoice);
        applyPromotion(invoice,customer,order,promotionCode);
        order.setTotalPrice(calculateTotal(invoice));
    }

}
