package org.route.dto.order;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@NoArgsConstructor
@Getter
public class OrderItemRequest {
    private int itemId;
    private int quantity;

}
