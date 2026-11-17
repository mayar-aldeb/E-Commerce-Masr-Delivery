package org.route.model.Items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.model.Restaurant;


import java.math.BigDecimal;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public  abstract class MenuItem {
    private  int id;
    private String name;
    private BigDecimal price;
    private String cuisine;
    private Restaurant restaurant;
    private int preparationTime;
    private int stock;
    private boolean available;

    public MenuItem(int id, String name, String cuisine, BigDecimal price, int preparationTime,int stock) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.price=price;
        this.preparationTime = preparationTime;
        this.available = true;
        this.stock=stock;
    }

    public BigDecimal calculatePrice(){
        return BigDecimal.ZERO;
    }



    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem item = (MenuItem) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
