package org.route.model.orderStatus;

import org.route.exception.OrderException;
import org.route.model.Order;
import org.springframework.http.HttpStatus;

public class CancelState extends OrderState{


    @Override
    public  void cancel(Order order){
        throw new OrderException(HttpStatus.BAD_REQUEST,"order already cancelled");
    }

}
