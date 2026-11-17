package org.route.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.dto.customer.CustomerResponse;
import org.route.dto.restaurant.MenuItemResponse;
import org.route.enums.OrderStatus;
import org.route.model.Address;
import org.route.model.orderStatus.OrderState;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse {
    private CustomerResponse customer;
    private Address deliveryAddress;
    private Map<MenuItemResponse,OrderItemResponse> items;
    private LocalDateTime placedAt;
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private OrderState state;
    private int orderPriority;
}
