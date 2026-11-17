package org.route.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.model.Address;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest {

    private int customerId;
    private int restaurantId;
    private Address deliveryAddress;
    private List<OrderItemRequest> orderItems;
    private String promotionCode;

}
