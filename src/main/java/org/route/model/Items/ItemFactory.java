package org.route.model.Items;

import org.mapstruct.ObjectFactory;
import org.route.dto.restaurant.MenuItemRequest;
import org.springframework.stereotype.Component;


@Component
public class ItemFactory {
    @ObjectFactory
    public static MenuItem create(MenuItemRequest menuItemRequest){
        return switch (menuItemRequest.getItemType()){
            case "combo"->
                new Combo();
            case "standard"->
                new StandardItem();
            case "weighted"->
                new WeightedItem();
            default -> throw new IllegalStateException("Unexpected value: " + menuItemRequest.getItemType());
        };
    }
}
