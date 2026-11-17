package org.route.model.orderStatus;

import org.route.enums.OrderStatus;
import org.route.exception.OrderException;
import org.route.model.Order;
import org.springframework.http.HttpStatus;

public class OutForDeliveryState extends OrderState{


    @Override
    public void deliver(Order order) {
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setState(new DeliveredState());

    }

    @Override
    public void outForDelivery(Order order){
        throw new OrderException(HttpStatus.BAD_REQUEST,"order already out for delivery");
    }

}
