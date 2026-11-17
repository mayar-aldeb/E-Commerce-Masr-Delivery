package org.route.model.Items;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@NoArgsConstructor
public class WeightedItem extends MenuItem {



    @Override
    public BigDecimal calculatePrice() {
        return getPrice();
    }
}
