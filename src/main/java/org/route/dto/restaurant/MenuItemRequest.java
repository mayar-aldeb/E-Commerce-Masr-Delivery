package org.route.dto.restaurant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class MenuItemRequest {
    private String itemType;
    private String name;
    private BigDecimal price;
    private String cuisine;
    private int restaurantId;
    private int preparationTime;
    private int stock;

}
