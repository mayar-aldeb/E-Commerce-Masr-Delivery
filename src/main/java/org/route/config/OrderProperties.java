package org.route.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@Getter
@Setter
@ConfigurationProperties(prefix = "order")
public class OrderProperties {
    Integer deliveryFree;
    BigDecimal deliveryBase;
    BigDecimal deliveryFeePerKm;
    BigDecimal serviceRate;
}
