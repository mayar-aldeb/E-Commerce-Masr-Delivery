package org.route.model;

import lombok.*;
import org.route.enums.OrderStatus;
import org.route.model.Items.MenuItem;
import org.route.model.Items.OrderItem;
import org.route.model.orderStatus.OrderState;
import org.route.model.orderStatus.PlacedState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private int id;
    private Customer customer;
    private Restaurant restaurant;
    private Address deliveryAddress;
    @Builder.Default
    private Map<MenuItem,OrderItem> items=new LinkedHashMap<>();
    @Builder.Default
    private LocalDateTime placedAt =LocalDateTime.now();
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
    @Builder.Default
    private OrderState state=new PlacedState();
    private int orderPriority;


}
