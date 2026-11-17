package org.route.model.orderStatus;

import org.route.enums.OrderStatus;
import org.route.exception.OrderException;
import org.route.model.Order;
import org.springframework.http.HttpStatus;

public class AssignedState extends OrderState{


    @Override
    public void outForDelivery(Order order) {
        order.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
        order.setState(new OutForDeliveryState());

    }

    @Override
    public void cancel(Order order){
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setState(new CancelState());

    }

    @Override
    public void assign(Order order){
        throw new OrderException(HttpStatus.BAD_REQUEST,"order already assigned");

    }
}
