package org.route.model.restaurntFilterObserver;

import org.route.model.Restaurant;

import java.util.List;



public record CuisineFilter(String cuisine) implements RestaurantFilterListener {

    @Override
    public List<Restaurant> filterRestaurants(List<Restaurant> restaurants) {
        return restaurants.stream().
                filter(restaurant -> restaurant.getCuisines().contains(cuisine)).
                toList();
    }
}
