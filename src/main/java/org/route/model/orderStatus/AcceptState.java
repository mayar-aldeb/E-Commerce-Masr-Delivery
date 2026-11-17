package org.route.model.orderStatus;

import org.route.enums.OrderStatus;
import org.route.exception.OrderException;
import org.route.model.Order;
import org.springframework.http.HttpStatus;

public class AcceptState extends OrderState{



    @Override
    public void prepare(Order order) {
        order.setOrderStatus(OrderStatus.PREPARING);
        order.setState(new PreparingState());
    }

    @Override
    public void cancel(Order order){
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setState(new CancelState());
    }

    @Override
    public void accept(Order order) {
        throw new OrderException(HttpStatus.BAD_REQUEST,"order already accepted");
    }
}
