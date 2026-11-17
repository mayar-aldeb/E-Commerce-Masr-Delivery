package org.route.controller;

import lombok.RequiredArgsConstructor;
import org.route.dto.order.OrderResponse;
import org.route.dto.restaurant.MenuItemRequest;
import org.route.platform.RestaurantPlatform;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
//     ========================================
//               Restaurant Platform
//     ========================================

    private final RestaurantPlatform restaurantPlatform;

    //1.Accept or reject pending order.
    @PatchMapping("/acceptOrder{orderId}")
    public void acceptOrder(@PathVariable int orderId){
        restaurantPlatform.markOrderAsAccepted(orderId);
    }

    //2.Mark an order preparing.
    @PatchMapping("/prepareOrder{orderId}")
    public void prepareOrder(@PathVariable int orderId){
        restaurantPlatform.markOrderAsPreparing(orderId);
    }

    //3.Mark an order ready.
    @PatchMapping("/readyOrder{orderId}")
    public void readyOrder(@PathVariable int orderId){
        restaurantPlatform.markOrderAsReady(orderId);
    }

    //4.Make item available.
    @PatchMapping("/availableItem")
    public void availableItem(@RequestParam int restaurantId,@RequestParam int menuItemId){
        restaurantPlatform.toggleItemAvailability(restaurantId,menuItemId,true);
    }

    //5.Make item unavailable.
    @PatchMapping("/unavailableItem")
    public void unavailableItem(@RequestParam int restaurantId,@RequestParam int menuItemId){
        restaurantPlatform.toggleItemAvailability(restaurantId,menuItemId,false);
    }

    //6.Add menu item.
    @PostMapping("/newMenuItem")
    public void createMenuItem(@RequestBody MenuItemRequest menuItemRequest){
        restaurantPlatform.addMenuItem(menuItemRequest);
    }

    //7.Remove menu item.
    @DeleteMapping("/removeMenuItem")
    public void removeMenuItem(@RequestParam int restaurantId,@RequestParam int menuItemId){
        restaurantPlatform.removeMenuItem(restaurantId,menuItemId);
    }
    
    //8.add item to combo.
    @PostMapping("/newComboItem")
    public void addComboItem(@RequestBody int restaurantId,@RequestParam int comboId,@RequestParam int menuItemId){
        restaurantPlatform.addComboItem(restaurantId,comboId,menuItemId);
    }

    //9.remove item from combo.
    @DeleteMapping("/removeComboItem")
    public void removeComboItem(@RequestParam int restaurantId,@RequestParam int comboId,@RequestParam int menuItemId){
        restaurantPlatform.removeComboItem(restaurantId,comboId,menuItemId);
    }

    //10.Adjust daily stock.
    @PatchMapping("/newStock")
    public void newStock(@RequestParam int restaurantId,@RequestParam int menuItemId,@RequestParam int stock){
        restaurantPlatform.adjustStock(restaurantId,menuItemId,stock);
    }

    //11.View today’s orders and revenue.
    @GetMapping("/newOrders{restaurantId}")
    public List<OrderResponse> newOrders(@PathVariable int restaurantId){
        return restaurantPlatform.displayTodayOrders(restaurantId);
    }

}
