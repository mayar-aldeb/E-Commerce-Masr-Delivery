package org.route.mapper;

import org.mapstruct.Mapper;
import org.route.dto.restaurant.MenuItemRequest;
import org.route.dto.restaurant.MenuItemResponse;
import org.route.model.Items.ItemFactory;
import org.route.model.Items.MenuItem;

@Mapper(componentModel = "spring",uses = {ItemFactory.class})
public interface MenuItemMapper {


    MenuItem toEntity(MenuItemRequest request);

    MenuItemResponse toMenuItemResponse(MenuItem item);
}
