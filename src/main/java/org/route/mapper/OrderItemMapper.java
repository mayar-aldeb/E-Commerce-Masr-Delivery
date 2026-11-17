package org.route.mapper;

import org.mapstruct.Mapper;
import org.route.dto.order.OrderItemRequest;
import org.route.dto.order.OrderItemResponse;
import org.route.model.Items.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponse toOrderItemResponse(OrderItem orderItem);
    OrderItem  toOrderItem(OrderItemRequest orderItemRequest);
}
