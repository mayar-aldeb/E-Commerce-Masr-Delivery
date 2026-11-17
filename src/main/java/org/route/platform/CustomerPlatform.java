package org.route.platform;

import org.route.dto.order.OrderItemRequest;
import org.route.dto.order.OrderRequest;
import org.route.dto.order.OrderResponse;
import org.route.dto.order.orderStatusResponse;
import org.route.dto.restaurant.MenuItemResponse;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.enums.OrderStatus;
import org.route.exception.*;
import lombok.RequiredArgsConstructor;
import org.route.mapper.MenuItemMapper;
import org.route.mapper.OrderMapper;
import org.route.mapper.RestaurantMapper;
import org.route.model.*;
import org.route.model.restaurntFilterObserver.RestaurantFilterListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.route.repository.*;
import org.route.service.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


@Component
@RequiredArgsConstructor
public class CustomerPlatform {

    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final OrderInvoiceService invoiceService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final MenuItemMapper menuItemMapper;
    private final RestaurantMapper restaurantMapper;


    public List<RestaurantResponse> browseRestaurants(List<RestaurantFilterListener> filters){
        return restaurantService.browseRestaurants(filters).stream().map(restaurantMapper::toRestaurantResponse).toList();
    }

    public List<RestaurantResponse> searchRestaurants( String search){
        return restaurantService.searchRestaurants(search).stream().map(restaurantMapper::toRestaurantResponse).toList();
    }

    public void placeOrder(OrderRequest orderRequest){
        Order order =orderMapper.toOrder(orderRequest);
        order.setRestaurant(restaurantService.findRestaurantById(orderRequest.getRestaurantId()));
        order.setCustomer(customerService.findCustomerById(orderRequest.getCustomerId()));
        if(order.getRestaurant().getItems().isEmpty())
            throw new OrderException(HttpStatus.BAD_REQUEST, "Restaurant has no items");
        for(OrderItemRequest itemRequest :orderRequest.getOrderItems()){
            orderService.addItemToOrder(order, itemRequest.getItemId(), itemRequest.getQuantity());
        }
         orderService.placeOrder(order);
        invoiceService.setInvoice(order,order.getCustomer(),orderRequest.getPromotionCode());
    }

    public void payInvoice(int orderId){
        Order order =orderService.findOrderById(orderId);
        if(order.getOrderStatus()== OrderStatus.CANCELLED)
            throw new OrderException(HttpStatus.BAD_REQUEST, "Order cancelled");

        OrderInvoice invoice =invoiceService.findInvoiceById(order);
        if(invoice.isPayed()){
           throw new OrderException(HttpStatus.BAD_REQUEST, "Order payment already paid");
        }
        customerService.payFromWallet(order.getCustomer(),invoiceService.calculateTotal(invoice));
        invoice.setPayed(true);
    }

    public orderStatusResponse trackOrder(int orderId){
        Order order =orderService.findOrderById(orderId);
        Duration elapsedTime = Duration.between(order.getPlacedAt(), LocalDateTime.now());
        return new orderStatusResponse(order.getOrderStatus(), elapsedTime);
    }

    public void cancelOrder(int  orderId){
        Order order =orderService.findOrderById(orderId);
        order.getState().cancel(order);
        orderService.refundOrderQuantity(order);
        OrderInvoice invoice=invoiceService.findInvoiceById(order);
        if(invoice.isPayed())
            customerService.refundToWallet(order.getCustomer(),order.getTotalPrice());
    }

    public List<OrderResponse> displayOrdersHistory(int customerId){
        Comparator<Order> comparator=Comparator.comparing(Order::getPlacedAt).reversed();
        Customer customer=customerService.findCustomerById(customerId);
        return orderRepository.findAll().stream().
                filter(o->o.getCustomer().getId()==customer.getId()).
                sorted(comparator).map(orderMapper::toOrderResponse).toList();
    }

    public List<MenuItemResponse> viewMenuItems(int restaurantId){
        return restaurantService.findAllMenuItems(restaurantId).stream()
                .map(menuItemMapper::toMenuItemResponse).toList();
    }

}
