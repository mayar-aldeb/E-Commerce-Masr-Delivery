package org.route.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantRequest {
    @NotBlank
    private String name;
    @NotBlank(message = "you have to inter district.")
    private String district;
    private double rate;

}
