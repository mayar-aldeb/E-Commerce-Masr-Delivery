package org.route.model.restaurntFilterObserver;

import org.route.model.Restaurant;

import java.util.List;


public record RateFilter(double rate) implements RestaurantFilterListener{

    @Override
    public List<Restaurant> filterRestaurants(List<Restaurant> restaurants) {
        return restaurants.stream().
                filter(restaurant -> restaurant.getRate()>=rate).
                toList();
    }
}
