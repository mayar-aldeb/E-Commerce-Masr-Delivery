package org.route.controller;

import lombok.RequiredArgsConstructor;
import org.route.dto.OrdersOfCustomer;
import org.route.dto.RestaurantRevenuesResponse;
import org.route.dto.customer.CustomerResponse;
import org.route.dto.restaurant.MenuItemResponse;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.dto.rider.RiderResponse;
import org.route.enums.OrderStatus;
import org.route.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController {
//    ========================================
//              Report Platform
//    ========================================
    private final AdminService adminService;

    //1.Total revenue for a given date range.
    @GetMapping("/TotalRevenue")
    public BigDecimal TotalRevenue(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
       return adminService.calculateTotalRevenue(startDate,endDate);
    }

    //2.The top five restaurants by revenue, for a given month.
    @GetMapping("/topRestaurants")
    public List<RestaurantRevenuesResponse> topRestaurants(@RequestParam int month, @RequestParam int year) {
       return adminService.displayTopFiveRestaurants(month, year);
    }

    //3.Average order value per district.
    @GetMapping("/ordersPerDistrict{district}")
    public double calculateAverageOrder( @PathVariable String district) {
        return adminService.calculateAverageOrder(district);
    }

    //4.Filterd restaurants by rate and completed orders.
    @GetMapping("/filterRestaurants")
    public List<RestaurantResponse> filterRestaurants(@RequestParam double rate,@RequestParam int completedOrders) {
        return  adminService.filterRestaurants(rate,completedOrders);
    }

    //5.Count of orders grouped by their current status.
    @GetMapping("/ordersPerStatus")
    public Map<OrderStatus,Long> countOrdersPerStatus() {
        return  adminService.countOrdersPerStatus();
    }

    //6.rider’s completed deliveries and	their average delivery duration.
    @GetMapping("/ridersDeliveries")
    public List<RiderResponse> displayRidersDeliveries() {
        return  adminService.displayRidersDeliveries();
    }

    //7.The most frequently ordered menu item across the whole org.route.platform.
    @GetMapping("/frequentlyMenuItem")
    public MenuItemResponse displayFrequentlyMenuItem() {
        return adminService.displayFrequentlyMenuItem();
    }

    //8.Customer’s full order history.
    @GetMapping("/ordersOfCustomer{id}")
    public OrdersOfCustomer displayOrdersOfCustomer(@PathVariable int id) {
        return adminService.displayOrdersOfCustomer(id);
    }

    //9.The peak ordering hour of the day.
    @GetMapping("/peakHour")
    public int displayPeakHour() {
        return adminService.findPeakHour();
    }

    //10.Every customer who has not ordered in the last thirty days.
    @GetMapping("/notActiveCustomers")
    public List<CustomerResponse> displayNotActiveCustomers() {
        return adminService.findNotActiveCustomers();
    }

}
