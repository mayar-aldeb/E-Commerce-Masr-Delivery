package org.route.platform;

import lombok.RequiredArgsConstructor;
import org.route.dto.order.OrderResponse;
import org.route.exception.RiderException;
import org.route.mapper.OrderMapper;
import org.route.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.route.repository.*;
import org.route.service.*;
import org.route.validation.RiderValidation;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RiderPlatform {
    private final CustomerService customerService;
    private final RiderService riderService;
    private final ReadyOrdersRepository readyOrdersRepository;
    private final OrderMapper orderMapper;



    public void goOnDuty(int riderId){
        Rider rider =riderService.findRiderById(riderId);
        if(rider.isAvailable())
            throw new RiderException(HttpStatus.BAD_REQUEST,"rider is already on duty.");
        rider.setAvailable(true);
    }

    public void goOffDuty(int riderId){
        Rider rider =riderService.findRiderById(riderId);
        if(!rider.isAvailable())
            throw new RiderException(HttpStatus.BAD_REQUEST,"rider is already off duty.");
        rider.setAvailable(false);
    }

    public void assignOrder(int riderId){
        Rider rider =riderService.findRiderById(riderId);
        RiderValidation.validateRider(rider);
        if(rider.getActiveOrder()!=null)
            throw new RiderException(HttpStatus.BAD_REQUEST,"Rider is already has order.");
        if(readyOrdersRepository.findReadyOrders().isEmpty())
            throw new RiderException(HttpStatus.BAD_REQUEST,"no ready orders to deliver.");
        Order order=readyOrdersRepository.peekReadyOrder();
        order.getState().assign(order);
        readyOrdersRepository.removeReadyOrder();
        riderService.assignOrderToRider(rider,order);
    }

    public void markPickedUp(int riderId){
        Rider rider =riderService.findRiderById(riderId);
        if(rider.getActiveOrder()==null)
            throw new RiderException(HttpStatus.BAD_REQUEST,"rider has no orders assigned.");
        Order order=rider.getActiveOrder();
        order.getState().outForDelivery(order);
    }

    public OrderResponse viewAssignedOrder(int riderId){
        Rider rider =riderService.findRiderById(riderId);
        if(rider.getActiveOrder()==null)
            throw new RiderException(HttpStatus.BAD_REQUEST,"rider has no orders assigned.");
        Order order=rider.getActiveOrder();
        return orderMapper.toOrderResponse(order);
    }

    public void deliverOrder(int riderId){
        Rider rider =riderService.findRiderById(riderId);
        if(rider.getActiveOrder()==null)
            throw new RiderException(HttpStatus.BAD_REQUEST,"rider has no orders assigned.");
        Order order=rider.getActiveOrder();
        Restaurant restaurant =order.getRestaurant();
        Customer customer=order.getCustomer();
        order.getState().deliver(order);
        riderService.deliverOrder(rider,order);
        customerService.setCompletedOrderToCustomer(customer,order);
        restaurant.setCompletedOrders(restaurant.getCompletedOrders()+1);
    }

    public List<OrderResponse> personalDeliveryStatistics(int riderId){
        Rider rider =riderService.findRiderById(riderId);
        return rider.getCompletedDeliveries().stream().map(orderMapper::toOrderResponse).toList();
    }



}
