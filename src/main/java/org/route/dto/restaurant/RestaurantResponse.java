package org.route.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.model.Items.MenuItem;

import java.util.Map;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class RestaurantResponse {

    @NotBlank
    private String name;
    @NotBlank(message = "you have to inter district.")
    private String district;
    private double rate;
    private boolean open;
    private int completedOrders;
    private Map<Integer, MenuItem>  items;
    private Set<String> cuisines;
}
