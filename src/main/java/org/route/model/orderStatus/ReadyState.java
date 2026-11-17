package org.route.model.orderStatus;

import org.route.enums.OrderStatus;
import org.route.exception.OrderException;
import org.route.model.Order;
import org.springframework.http.HttpStatus;

public class ReadyState extends OrderState{

    @Override
    public void assign(Order order) {
        order.setOrderStatus(OrderStatus.ASSIGNED);
        order.setState(new AssignedState());
    }

    @Override
    public void cancel(Order order){
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setState(new CancelState());
    }

    public  void ready(Order order){
        throw new OrderException(HttpStatus.BAD_REQUEST,"order is already ready");
    }

}
