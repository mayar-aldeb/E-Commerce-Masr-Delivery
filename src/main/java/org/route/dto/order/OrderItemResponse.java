package org.route.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.dto.restaurant.MenuItemResponse;
@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponse {
    private MenuItemResponse item;
    private int quantity;
}
