package org.route.validation;

import org.route.exception.RestaurantException;
import org.route.model.Items.MenuItem;
import org.route.model.Restaurant;
import org.springframework.http.HttpStatus;

public class RestaurantValidation {

    public static void validateRate(double rate){
        if(rate >= 0 && rate <= 5)
            return;
        throw  new RestaurantException(HttpStatus.BAD_REQUEST,"Invalid restaurant rate.");
    }

    public static void isOpen(Restaurant restaurant) {
        if(!restaurant.isOpen())
            throw new RestaurantException(HttpStatus.BAD_REQUEST,"Restaurant "+restaurant.getName()+" is closed.");
    }

    public static void validateStock(MenuItem item,int quantity){
        if(item.getStock()<quantity)
            throw new RestaurantException(HttpStatus.BAD_REQUEST,"Item doesn't have enough stock.");
    }

    public static void validateItem(MenuItem item){
        if(!item.isAvailable())
            throw new RestaurantException(HttpStatus.BAD_REQUEST,"item is not available.");
    }

}
