package org.route.model.orderPromotion.promotionCondition;

import lombok.Getter;
import lombok.Setter;
import org.route.exception.PromotionException;
import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class FirstTimeCustomerCondition implements PromotionCondition{


    @Override
    public void checkCondition(OrderInvoice invoice, Customer customer, Order order) {
        if(customer.getCompletedOrders() > 0)
            throw new PromotionException(HttpStatus.BAD_REQUEST,"promotion is restriction to first time customers only.");
    }
}
