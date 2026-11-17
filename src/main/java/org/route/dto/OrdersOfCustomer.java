package org.route.dto;

import lombok.Getter;
import lombok.Setter;
import org.route.dto.order.OrderResponse;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
public class OrdersOfCustomer {
    List<OrderResponse> orders;
    BigDecimal totalSpend;
}
