package org.route.model.orderPromotion;

import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.route.model.orderPromotion.promotionCondition.PromotionCondition;

import java.math.BigDecimal;
import java.util.List;

public class SubtotalDiscountPromotion extends Promotion {

    private final BigDecimal discountRate;
    private final BigDecimal maximumDiscountCap;

    public SubtotalDiscountPromotion(BigDecimal discountRate, BigDecimal maximumDiscountCap,String promotionCode,List<PromotionCondition> promotionCondition) {
        super(promotionCode,promotionCondition);
        this.discountRate = discountRate;
        this.maximumDiscountCap = maximumDiscountCap;
    }


    @Override
    public boolean applyPromotion(OrderInvoice invoice, Customer customer, Order order) {
        checkConditions(invoice,customer,order);
        BigDecimal discount=invoice.getSubTotal().multiply(discountRate);
        if(discount.compareTo(maximumDiscountCap)>0)
            discount=maximumDiscountCap;
        invoice.setSubTotal(invoice.getSubTotal().subtract(discount));
        return true;
    }



}
