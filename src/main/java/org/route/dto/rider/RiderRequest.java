package org.route.dto.rider;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.enums.VehicleType;
@Getter
@Setter
@NoArgsConstructor
public class RiderRequest {
    private String name;
    private VehicleType vehicleType;
}
