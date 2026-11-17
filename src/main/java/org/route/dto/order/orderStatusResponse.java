package org.route.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.route.enums.OrderStatus;

import java.time.Duration;
@Getter
@Setter
@AllArgsConstructor
public class orderStatusResponse {
    private OrderStatus orderStatus;
    private Duration elapsedTime;
}
