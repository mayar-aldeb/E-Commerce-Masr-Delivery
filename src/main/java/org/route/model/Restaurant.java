package org.route.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.route.model.Items.MenuItem;

import java.util.*;


@Getter
@Setter
public class Restaurant  {
    private static int count=1;
    private int id;
    private int itemCount;
    @NotBlank
    private String name;
    @NotBlank(message = "you have to inter district.")
    private String district;
    private double rate;
    private boolean open;
    private int completedOrders;
    private Map<Integer,MenuItem> items=new LinkedHashMap<>();
    private Set<String> cuisines=new HashSet<>();

    public Restaurant(String name, String district, double rate) {
        this.name = name;
        this.district = district;
        this.rate = rate;
        this.open = true;
        this.id=createId();
    }

    public static int createId(){
        return count++;
    }

    public void setItem(MenuItem item) {
        this.items.put(item.getId(),item);
    }



}
