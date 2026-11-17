package org.route.model.Items;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class Combo  extends MenuItem{

    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.1");
    private final Map<Integer,MenuItem> comboItems= new HashMap<>();



    @Override
    public BigDecimal calculatePrice() {
        BigDecimal discount=getPrice().multiply(DISCOUNT_RATE);
        return getPrice().subtract(discount);
    }

}
