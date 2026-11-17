package org.route.dto.restaurant;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MenuItemResponse {

    private String name;
    private BigDecimal price;
    private String cuisine;
    private int preparationTime;
    private int stock;
    private Map<Integer, MenuItemResponse> comboItems;

}
