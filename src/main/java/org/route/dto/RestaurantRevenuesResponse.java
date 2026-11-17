package org.route.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
public class RestaurantRevenuesResponse {
    private String RestaurantName;
    private int RestaurantId;
    private BigDecimal RestaurantRevenues;
}
