package org.route.dto.rider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.dto.order.OrderResponse;
import org.route.enums.VehicleType;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class RiderResponse {
    private int id;
    private String name;
    private VehicleType vehicleType;
    private OrderResponse activeOrder;
    private List<OrderResponse> completedDeliveries;
}
