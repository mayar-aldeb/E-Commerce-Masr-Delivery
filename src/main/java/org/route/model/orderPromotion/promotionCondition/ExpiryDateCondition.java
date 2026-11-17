package org.route.model.orderPromotion.promotionCondition;

import org.route.exception.PromotionException;
import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.springframework.http.HttpStatus;


public record ExpiryDateCondition(int expirationDate) implements PromotionCondition {


    @Override
    public void checkCondition(OrderInvoice invoice, Customer customer, Order order) {
        if (invoice.getOrderDate().getMonthValue() == expirationDate)
            throw new PromotionException(HttpStatus.BAD_REQUEST,"promotion is expired");
    }
}
