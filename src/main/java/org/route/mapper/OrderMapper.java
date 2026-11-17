package org.route.mapper;

import org.mapstruct.Mapper;
import org.route.dto.order.OrderRequest;
import org.route.dto.order.OrderResponse;
import org.route.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderRequest orderRequest);

    OrderResponse toOrderResponse(Order order);
}
