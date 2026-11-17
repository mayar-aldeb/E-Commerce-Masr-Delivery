package org.route.model.orderPromotion.promotionCondition;

import org.route.exception.PromotionException;
import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.springframework.http.HttpStatus;


public record DistrictCondition(String district) implements PromotionCondition {


    @Override
    public void checkCondition(OrderInvoice invoice, Customer customer, Order order) {
        if (!order.getDeliveryAddress().getDistrict().equals(district))
            throw new PromotionException(HttpStatus.BAD_REQUEST,"order district is not allowed for this promotion.");
    }
}
