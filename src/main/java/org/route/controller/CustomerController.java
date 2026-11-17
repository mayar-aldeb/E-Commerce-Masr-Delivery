package org.route.controller;

import lombok.RequiredArgsConstructor;
import org.route.dto.order.OrderRequest;
import org.route.dto.order.OrderResponse;
import org.route.dto.order.orderStatusResponse;
import org.route.dto.restaurant.MenuItemResponse;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.model.restaurntFilterObserver.RestaurantFilterListener;
import org.route.platform.CustomerPlatform;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
//       ========================================
//                Customer Platform
//       ========================================
    private final CustomerPlatform customerPlatform;

    //1.Browse restaurants.
    @GetMapping("/restaurants")
    public List<RestaurantResponse> browseRestaurants(@RequestBody List<RestaurantFilterListener> filters) {
        return customerPlatform.browseRestaurants(filters);
    }

    // 2.Search.
    @GetMapping("/restaurantsSearch")
    public List<RestaurantResponse> searchRestaurants(@RequestParam String search){
        return customerPlatform.searchRestaurants(search);
    }

    //3.Veiw Menu.
    @GetMapping("/menuItems{restaurantId}")
    public List<MenuItemResponse> browseMenuItems(@PathVariable Integer restaurantId){
        return customerPlatform.viewMenuItems(restaurantId);
    }

    //4.Place Order.
    @PostMapping("/newOrder")
    public void createOrder(@RequestBody OrderRequest orderRequest){
        customerPlatform.placeOrder(orderRequest);
    }

    //5.Pay from wallet.
    @PostMapping("/payOrder{orderId}")
    public void payOrder(@PathVariable int orderId){
        customerPlatform.payInvoice(orderId);
    }

    //6.Track order.
    @GetMapping("/orderStatus{orderId}")
    public orderStatusResponse orderStatus(@PathVariable int orderId){
        return customerPlatform.trackOrder(orderId);
    }

    //7.Cancel order.
    @PostMapping("/cancelOrder{orderId}")
    public void cancelOrder(@PathVariable int orderId){
        customerPlatform.cancelOrder(orderId);
    }

    //8.Order history.
    @GetMapping("/customerOrders{customerId}")
    public List<OrderResponse> displayOrders(@PathVariable int customerId){
        return customerPlatform.displayOrdersHistory(customerId);
    }
}
