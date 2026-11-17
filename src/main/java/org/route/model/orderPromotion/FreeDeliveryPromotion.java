package org.route.model.orderPromotion;

import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.route.model.orderPromotion.promotionCondition.PromotionCondition;

import java.math.BigDecimal;
import java.util.List;

public class FreeDeliveryPromotion extends Promotion {

    public FreeDeliveryPromotion(String promotionCode,List<PromotionCondition> promotionCondition) {
        super(promotionCode,promotionCondition);
    }

    @Override
    public boolean applyPromotion(OrderInvoice invoice, Customer customer, Order order) {
        checkConditions(invoice,customer,order);
        invoice.setDeliveryFee(BigDecimal.valueOf(0));
        return true;
    }


}
