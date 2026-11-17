package org.route.service;

import lombok.RequiredArgsConstructor;
import org.route.dto.Coordinates;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryDistanceService {
    private final GeocodingService geocodingService;
    private final RoutingService routingService;

    public double calculate(String restaurantAddress, String customerAddress){
        Coordinates restaurant = geocodingService.getCoordinates(restaurantAddress);
        Coordinates customer =geocodingService.getCoordinates(customerAddress);
        try {
            return routingService.calculateDistance(restaurant, customer);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
