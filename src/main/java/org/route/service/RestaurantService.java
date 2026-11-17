package org.route.service;

import org.route.dto.restaurant.RestaurantRequest;
import org.route.exception.RestaurantException;
import lombok.RequiredArgsConstructor;
import org.route.mapper.RestaurantMapper;
import org.route.model.*;
import org.route.model.Items.Combo;
import org.route.model.Items.MenuItem;
import org.route.model.restaurntFilterObserver.RestaurantFilterListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.route.repository.RestaurantRepository;
import org.route.validation.RestaurantValidation;

import java.util.*;


@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;




    public List<Restaurant> findAllRestaurants(){
        return restaurantRepository.findAll();
    }

    public void deleteRestaurant(int id){
        restaurantRepository.delete(id).orElseThrow(()->new RestaurantException(HttpStatus.NOT_FOUND,"Restaurant not found"));
    }


    public List<MenuItem> findAllMenuItems(int restaurantId){
        Restaurant restaurant=restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantException(HttpStatus.NOT_FOUND,"Restaurant not found"));
        return restaurant.getItems().values().stream().toList();
    }

    public void addItemToCombo(MenuItem item, Combo combo){
        combo.setPrice(combo.getPrice().add(item.getPrice()));
        combo.setPreparationTime(combo.getPreparationTime()+item.getPreparationTime());
        combo.getComboItems().put(item.getId(),item);
    }

    public void removeItemFromCombo(MenuItem item, Combo combo){
        combo.setPrice(combo.getPrice().subtract(item.getPrice()));
        combo.setPreparationTime(combo.getPreparationTime()-item.getPreparationTime());
        combo.getComboItems().remove(item.getId());
    }

    public List<Restaurant> browseRestaurants(List<RestaurantFilterListener> filters){
        List<Restaurant> filteredRestaurants=  restaurantRepository.findAll();
        for(var filter:filters)
            filteredRestaurants= filter.filterRestaurants(filteredRestaurants);
        return filteredRestaurants;
    }

    public List<Restaurant> searchRestaurants(String search){
        return restaurantRepository.findAll().stream().
                filter(restaurant -> restaurant.getCuisines().contains(search) ||
                        restaurant.getName().toLowerCase().contains(search.toLowerCase())).toList();
    }

    public Restaurant findRestaurantById(int id){
        Restaurant restaurant =restaurantRepository.findById(id).orElseThrow(()->new RestaurantException(HttpStatus.NOT_FOUND,"Restaurant not found"));
        RestaurantValidation.isOpen(restaurant);
        return restaurant;
    }

    public void createRestaurant(RestaurantRequest restaurantRequest){
        Restaurant restaurant = restaurantMapper.toRestaurant(restaurantRequest);
        RestaurantValidation.validateRate(restaurant.getRate());
        restaurantRepository.save(restaurant);
    }

    public void addItem(Restaurant restaurant, MenuItem item){
        restaurant.setItemCount(restaurant.getItemCount()+1);
        restaurant.getCuisines().add(item.getCuisine());
        item.setId(restaurant.getItemCount());
        restaurant.setItem(item);
    }


    public MenuItem checkItem(int itemId, Restaurant restaurant){
        if(!restaurant.getItems().containsKey(itemId))
            throw new RestaurantException(HttpStatus.NOT_FOUND,"Item not found");
        return restaurant.getItems().get(itemId);
    }

    public Combo findComboById(Map<Integer,MenuItem> items,int comboId){
        MenuItem item =items.get(comboId);
        if(item ==null)
            throw new RestaurantException(HttpStatus.NOT_FOUND,"Item not found");
        if(item instanceof Combo combo)
            return combo;

        throw  new RestaurantException(HttpStatus.NOT_FOUND,"item is not Combo");
    }

}
