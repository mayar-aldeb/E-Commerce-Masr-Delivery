package org.route.model.restaurntFilterObserver;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.route.model.Restaurant;

import java.util.List;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "filterType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CuisineFilter.class, name = "CuisineFilter"),
        @JsonSubTypes.Type(value = DistrictFilter.class, name = "DistrictFilter"),
        @JsonSubTypes.Type(value = PriceCeilingFilter.class, name = "PriceCeilingFilter"),
        @JsonSubTypes.Type(value = RateFilter.class, name = "RateFilter")
})
public interface RestaurantFilterListener {
    List<Restaurant> filterRestaurants(List<Restaurant> restaurants);
}
