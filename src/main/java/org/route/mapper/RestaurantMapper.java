package org.route.mapper;

import org.mapstruct.Mapper;
import org.route.dto.restaurant.RestaurantRequest;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.model.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantResponse toRestaurantResponse(Restaurant restaurant);

    Restaurant toRestaurant(RestaurantRequest restaurantRequest);

}
