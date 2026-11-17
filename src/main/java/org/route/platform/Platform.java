package org.route.platform;

import lombok.RequiredArgsConstructor;
import org.route.dto.customer.CustomerResponse;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.dto.rider.RiderResponse;
import org.route.mapper.CustomerMapper;
import org.route.mapper.RestaurantMapper;
import org.route.mapper.RiderMapper;
import org.route.model.*;
import org.springframework.stereotype.Component;
import org.route.service.*;


@Component
@RequiredArgsConstructor
public class Platform {
    private final RestaurantService restaurantService;
    private final CustomerService customerService;
    private final RiderService riderService;
    private final RestaurantMapper restaurantMapper;
    private final CustomerMapper customerMapper;
    private final RiderMapper riderMapper;


    public RestaurantResponse displayRestaurantById(int id){
        return restaurantMapper.toRestaurantResponse(restaurantService.findRestaurantById(id));
    }

    public RiderResponse displayRiderById(int id){
        return riderMapper.toResponse(riderService.findRiderById(id));
    }

    public CustomerResponse displayCustomerById(int id){
        return customerMapper.toCustomerResponse(customerService.findCustomerById(id));
    }




}
