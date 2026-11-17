package org.route.model;

import lombok.Getter;
import lombok.Setter;
import org.route.model.orderPromotion.Promotion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderInvoice {

    private Order order;
    private BigDecimal subTotal;
    private BigDecimal deliveryFee;
    private BigDecimal serviceFee;
    private LocalDateTime orderDate;
    private boolean payed;
    private Promotion promotion;


    public OrderInvoice( Order order) {
        this.order=order;
        this.orderDate=order.getPlacedAt();
        promotion=null;
        payed=false;
    }


}
