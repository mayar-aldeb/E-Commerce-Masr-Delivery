package org.route.model.orderPromotion;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import org.route.exception.PromotionException;
import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.route.model.orderPromotion.promotionCondition.PromotionCondition;

import java.util.List;
@Getter
@Setter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "promotionType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FixedAmountDiscountPromotion.class, name = "FixedAmount"),
        @JsonSubTypes.Type(value = FreeDeliveryPromotion.class, name = "FreeDelivery"),
        @JsonSubTypes.Type(value = SubtotalDiscountPromotion.class, name = "SubtotalDiscount")
})
public abstract class Promotion {
    private List<PromotionCondition> condition;
    private String promotionCode;

    public Promotion(String promotionCode,List<PromotionCondition> condition) {
        this.promotionCode = promotionCode;
        this.condition=condition;
    }

    public void checkConditions(OrderInvoice invoice, Customer customer, Order order)throws PromotionException {
        condition.forEach(c->c.checkCondition(invoice,customer,order));
    }
    public abstract boolean applyPromotion(OrderInvoice invoice, Customer customer, Order order) throws PromotionException;




}
