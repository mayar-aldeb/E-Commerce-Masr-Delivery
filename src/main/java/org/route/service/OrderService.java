package org.route.service;

import org.route.enums.CustomerLoyaltyTier;
import org.route.enums.OrderStatus;
import org.route.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.route.model.*;
import org.route.model.Items.Combo;
import org.route.model.Items.MenuItem;
import org.route.model.Items.OrderItem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.route.repository.OrderRepository;
import org.route.validation.RestaurantValidation;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class OrderService {
    private static int orderCount =0;
    private final RestaurantService restaurantService;
    private final OrderRepository orderRepository;


    public BigDecimal calculateSubTotal(OrderItem item){
        return item.getItem().calculatePrice().multiply(BigDecimal.valueOf(item.getQuantity()));
    }

    public void calculateOrderTotalPrice(Order order){
        BigDecimal totalPrice= BigDecimal.valueOf(0);
        for(OrderItem orderItem:order.getItems().values()){
            totalPrice=totalPrice.add(calculateSubTotal(orderItem));
        }
        order.setTotalPrice(totalPrice);
    }

    public void createOrderItem(Order order,OrderItem newOrderItem){
        OrderItem orderItem=order.getItems().get(newOrderItem.getItem());
        if(orderItem!=null){
            orderItem.setQuantity(orderItem.getQuantity()+newOrderItem.getQuantity());
            return;
        }
        order.getItems().put(newOrderItem.getItem(),newOrderItem);
    }

    public Order findOrderById(int orderId){
        return orderRepository.findById(orderId).orElseThrow(()->new OrderException(HttpStatus.NOT_FOUND,"Order ID not found!"));
    }

    public void addItemToOrder(Order order,int menuItemId,int quantity){
        MenuItem menuItem =restaurantService.checkItem(menuItemId,order.getRestaurant());
        if(menuItem instanceof Combo combo){
            if(combo.getComboItems().isEmpty())
                throw new OrderException(HttpStatus.BAD_REQUEST,"you can't add empty combo.");
        }
        RestaurantValidation.validateItem(menuItem);
        RestaurantValidation.validateStock(menuItem,quantity);
        OrderItem orderItem = new OrderItem(quantity,menuItem) ;
        orderItem.getItem().setStock(menuItem.getStock()-quantity);
        createOrderItem(order,orderItem);
    }


    public void refundOrderQuantity(Order order){
        for(OrderItem orderItem:order.getItems().values()){
            orderItem.getItem().setStock(orderItem.getItem().getStock()+orderItem.getQuantity());
        }
    }

    public void createOrder(Order order){
        order.setId(++orderCount);
        order.setOrderStatus(OrderStatus.PLACED);
        order.setOrderPriority(order.getCustomer().getLoyaltyTier() == CustomerLoyaltyTier.GOLD ? 0 : 1);
    }

    public void placeOrder(Order order){
        createOrder(order);
        calculateOrderTotalPrice(order);
        orderRepository.save(order);
    }

    public boolean checkOrderWithUnavailableItems(Order order){

        return order.getItems().values().stream()
                .anyMatch(item->!item.getItem().isAvailable());
    }

}
