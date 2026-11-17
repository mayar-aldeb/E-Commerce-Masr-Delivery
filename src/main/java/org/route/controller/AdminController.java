package org.route.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.route.dto.customer.CustomerRequest;
import org.route.dto.customer.CustomerResponse;
import org.route.dto.restaurant.RestaurantRequest;
import org.route.dto.restaurant.RestaurantResponse;

import org.route.dto.rider.RiderRequest;
import org.route.model.Rider;
import org.route.model.orderPromotion.Promotion;
import org.route.platform.AdminPlatform;
import org.route.service.AdminService;
import org.route.service.CustomerService;
import org.route.service.RestaurantService;
import org.route.service.RiderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final RestaurantService restaurantService;
    private final RiderService riderService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final AdminPlatform adminPlatform;

    //1.Add restaurant.
    @PostMapping("/newRestaurant")
    public void addRestaurant(@RequestBody @Valid RestaurantRequest restaurant){
        restaurantService.createRestaurant(restaurant);
    }

    //2.Remove restaurant.
    @DeleteMapping("/removeRestaurant{id}")
    public void removeRestaurant(@PathVariable int id){
        restaurantService.deleteRestaurant(id);
    }

    //3.Add rider.
    @PostMapping("/addRider")
    public void addRider(@RequestBody RiderRequest rider){
        riderService.addRider(rider);
    }

    //4.Add customer.
    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody @Valid CustomerRequest customer){
        customerService.addCustomer(customer);
    }

    //5.Create promotion.
    @PostMapping("/newPromotion")
    public void createPromotion(@RequestBody @Valid Promotion promotion){
        adminService.addPromotion(promotion);
    }

    @GetMapping("/restaurants")
    public List<RestaurantResponse> displayRestaurants(){
        return adminPlatform.displayRestaurant() ;
    }

    @GetMapping("/riders")
    public List<Rider> displayRiders(){
        return riderService.displayRiders();
    }

    @GetMapping("/customers")
    public List<CustomerResponse> displayCustomers(){
        return customerService.findAllCustomers();
    }

    @GetMapping("/promotions")
    public List<Promotion> displayPromotions(){
        return adminService.getPromotions();
    }



}
