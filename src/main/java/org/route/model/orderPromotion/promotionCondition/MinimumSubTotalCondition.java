package org.route.model.orderPromotion.promotionCondition;

import org.route.exception.PromotionException;
import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public record MinimumSubTotalCondition(BigDecimal minimumTotal) implements PromotionCondition {

    @Override
    public void checkCondition(OrderInvoice invoice, Customer customer, Order order) {
        if (invoice.getSubTotal().compareTo(minimumTotal) < 0)
            throw new PromotionException(HttpStatus.BAD_REQUEST,"total didn't reach minimum total for promotion.");
    }
}
