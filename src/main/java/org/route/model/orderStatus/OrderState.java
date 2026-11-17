package org.route.model.orderStatus;

import org.route.exception.OrderException;
import org.route.model.Order;
import org.springframework.http.HttpStatus;

public abstract class OrderState {

    public void accept(Order order){
       throw new OrderException(HttpStatus.BAD_REQUEST,"Cannot accept order");
   }

   public  void prepare(Order order){
       throw new OrderException(HttpStatus.BAD_REQUEST,"Cannot prepare order");
   }

   public  void ready(Order order){
       throw new OrderException(HttpStatus.BAD_REQUEST,"Cannot get order ready");
   }

   public  void assign(Order order){
       throw new OrderException(HttpStatus.BAD_REQUEST,"Cannot assign order");
   }

   public  void outForDelivery(Order order){
       throw new OrderException(HttpStatus.BAD_REQUEST,"Cannot get order out for delivery");
   }

   public  void deliver(Order order){
       throw new OrderException(HttpStatus.BAD_REQUEST,"Cannot deliver order");
   }

   public  void cancel(Order order){
       throw new OrderException(HttpStatus.BAD_REQUEST,"Cannot cancel order");
   }


}
