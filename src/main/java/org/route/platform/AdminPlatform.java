package org.route.platform;

import lombok.RequiredArgsConstructor;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.mapper.RestaurantMapper;
import org.route.model.*;
import org.springframework.stereotype.Component;
import org.route.repository.*;
import org.route.service.*;


import java.util.*;

@Component
@RequiredArgsConstructor
public class AdminPlatform{

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    public List<RestaurantResponse> displayRestaurant(){
        return restaurantService.findAllRestaurants().stream().map(restaurantMapper::toRestaurantResponse).toList();
    }

}
