package org.route.model.orderStatus;

import org.route.exception.OrderException;
import org.route.model.Order;
import org.springframework.http.HttpStatus;

public class DeliveredState extends OrderState {


    @Override
    public  void deliver(Order order){
        throw new OrderException(HttpStatus.BAD_REQUEST,"order already delivered");
    }

}
