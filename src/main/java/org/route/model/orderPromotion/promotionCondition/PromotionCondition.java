package org.route.model.orderPromotion.promotionCondition;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.route.exception.PromotionException;
import org.route.model.Customer;
import org.route.model.Order;
import org.route.model.OrderInvoice;


@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "conditionType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DistrictCondition.class, name = "DistrictCondition"),
        @JsonSubTypes.Type(value = ExpiryDateCondition.class, name = "ExpiryDateCondition"),
        @JsonSubTypes.Type(value = FirstTimeCustomerCondition.class, name = "FirstTimeCustomerCondition"),
        @JsonSubTypes.Type(value = MinimumSubTotalCondition.class, name = "MinimumSubTotalCondition")
})
public interface PromotionCondition {

    void checkCondition(OrderInvoice invoice, Customer customer, Order order) throws PromotionException;
}
