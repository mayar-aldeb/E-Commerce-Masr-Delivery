package org.route.repository;

import jakarta.annotation.PostConstruct;
import org.route.model.Items.StandardItem;
import org.route.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

@Repository
public class RestaurantRepository {
    private final Map<Integer, Restaurant> restaurants= new HashMap<>();


    @PostConstruct
    public void init() {

        restaurants.put(1, new Restaurant("Kazouza", "Maadi", 4.5));
        restaurants.put(2, new Restaurant("Zooba", "Zamalek", 4.7));
        restaurants.put(3, new Restaurant("Buffalo Burger", "Nasr City", 3.2));
        restaurants.put(4, new Restaurant("Chicken & Ribs", "Heliopolis", 2.8));
        restaurants.put(5, new Restaurant("Abou El Sid", "Zamalek", 4.1));
        restaurants.put(6, new Restaurant("Crave", "New Cairo", 3.5));
        restaurants.put(7, new Restaurant("Pizza Station", "6th of October", 2.4));
        restaurants.put(8, new Restaurant("The Grill", "Dokki", 3.0));
        restaurants.put(9, new Restaurant("Koshary Abou Tarek", "Downtown", 4.6));
        restaurants.put(10, new Restaurant("Burger Factory", "Sheikh Zayed", 1.9));
        Restaurant restaurant = restaurants.get(1);
        restaurant.setItem(new StandardItem(1,"frise","amirican", BigDecimal.valueOf(50),40,5));
        restaurant.setItem(new StandardItem(2,"burger","amirican", BigDecimal.valueOf(100),44,6));
        restaurant.setItem(new StandardItem(3,"pasta","italy", BigDecimal.valueOf(150),100,10));
    }
    public Optional<Restaurant> findById(int id) {
        return Optional.ofNullable(restaurants.get(id));
    }

    public List<Restaurant> findAll() {
        Comparator<Restaurant> comparator=Comparator.comparing(Restaurant::getRate).reversed().thenComparing(Restaurant::getName);
        return restaurants.values().stream().sorted(comparator).toList();
    }

    public void save(Restaurant restaurant) {
        restaurants.put(restaurant.getId(), restaurant);
    }

    public Optional<Restaurant>  delete(int id) {
        return Optional.ofNullable(restaurants.remove(id));
    }



}
