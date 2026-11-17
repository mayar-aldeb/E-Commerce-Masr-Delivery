package org.route.controller;

import lombok.RequiredArgsConstructor;
import org.route.dto.customer.CustomerResponse;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.dto.rider.RiderResponse;
import org.route.platform.Platform;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class LogInController {
//     ========================================
//               Masr Delivery Platform
//     ========================================

    private final Platform platform;


    //1.Customer.
    @GetMapping("/customers{id}")
    public CustomerResponse findCustomerById(@PathVariable int id){
        return platform.displayCustomerById(id);
    }

    //2.Restaurant.
    @GetMapping("/restaurants{id}")
    public RestaurantResponse getRestaurantById(@PathVariable int id){
        return platform.displayRestaurantById(id);
    }

    //3.Rider.
    @GetMapping("/riders{id}")
    public RiderResponse displayRiders(@PathVariable int id){
        return platform.displayRiderById(id);
    }

}
