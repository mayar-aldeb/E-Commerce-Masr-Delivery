package org.route.platform;

import org.route.dto.order.OrderResponse;
import org.route.dto.restaurant.MenuItemRequest;
import lombok.RequiredArgsConstructor;
import org.route.exception.OrderException;
import org.route.exception.RestaurantException;
import org.route.mapper.MenuItemMapper;
import org.route.mapper.OrderMapper;
import org.route.model.*;
import org.route.model.Items.Combo;
import org.route.model.Items.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.route.repository.*;
import org.route.service.*;

import java.time.LocalDate;
import java.util.List;


@Component
@RequiredArgsConstructor
public class RestaurantPlatform {

    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final CustomerService customerService;
    private final OrderInvoiceService invoiceService;
    private final OrderRepository orderRepository;
    private final ReadyOrdersRepository readyOrdersRepository;
    private final OrderMapper orderMapper;
    private final MenuItemMapper menuItemMapper;


    public void markOrderAsAccepted(int orderId){
        Order order=orderService.findOrderById(orderId);
        if(orderService.checkOrderWithUnavailableItems(order)){
            order.getState().cancel(order);
            orderService.refundOrderQuantity(order);
            OrderInvoice invoice=invoiceService.findInvoiceById(order);
            Customer customer=order.getCustomer();
            if(invoice.isPayed()){
                customerService.refundToWallet(customer,order.getTotalPrice());
            }
            throw new OrderException(HttpStatus.BAD_REQUEST,"Order has unavailable Item");
        }
        order.getState().accept(order);
    }

    public void markOrderAsPreparing(int orderId){
        Order order=orderService.findOrderById(orderId);
        order.getState().prepare(order);
    }

    public void markOrderAsReady(int orderId){
        Order order=orderService.findOrderById(orderId);

        OrderInvoice invoice=invoiceService.findInvoiceById(order);
        if(!invoice.isPayed())
            throw new OrderException(HttpStatus.BAD_REQUEST,"order is unpaid cannot be ready.");
        order.getState().ready(order);
        readyOrdersRepository.addOrder(order);

    }

    public void toggleItemAvailability(int restaurantId,int menuItemId,boolean availability){
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        MenuItem item=restaurantService.checkItem(menuItemId,restaurant);
        item.setAvailable(availability);
    }

    public void addMenuItem(MenuItemRequest menuItemRequest){
        Restaurant restaurant=restaurantService.findRestaurantById(menuItemRequest.getRestaurantId());
        MenuItem item=menuItemMapper.toEntity(menuItemRequest);
        restaurantService.addItem(restaurant,item);
    }

    public void addComboItem(int restaurantId, int comboId,int itemId){
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        Combo combo =restaurantService.findComboById(restaurant.getItems(), comboId);
        MenuItem item= restaurantService.checkItem(itemId,restaurant);
        restaurantService.addItemToCombo(item, combo);
    }

    public void removeMenuItem(int restaurantId,int menuItemId){
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);

        MenuItem item=restaurant.getItems().remove(menuItemId);
        if(item==null)
            throw new RestaurantException(HttpStatus.NOT_FOUND,"Item does not exist");
    }

    public void removeComboItem(int restaurantId,int comboId,int itemId){
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        Combo combo =restaurantService.findComboById(restaurant.getItems(), comboId);
        MenuItem item=combo.getComboItems().get(itemId);
        if(item==null)
            throw  new RestaurantException(HttpStatus.NOT_FOUND,"Item does not exist");
        restaurantService.removeItemFromCombo(item,combo);

    }

    public void adjustStock(int restaurantId,int menuItemId,int stock){
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        MenuItem item=restaurantService.checkItem(menuItemId,restaurant);
        item.setStock(stock+item.getStock());
    }

    public List<OrderResponse> displayTodayOrders(int restaurantId){
        Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
        return orderRepository.findAll().stream()
                .filter(o->o.getRestaurant().getId()==restaurant.getId())
                .filter(o->o.getPlacedAt().toLocalDate().equals(LocalDate.now()))
                .map(orderMapper::toOrderResponse).toList();
    }


}
