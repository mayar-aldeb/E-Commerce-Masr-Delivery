package org.route.dto.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.model.Address;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CustomerRequest {
    @NotBlank
    private String name;
    @Pattern(regexp = "01[0125]\\d{8}",message = "invalid phone number.")
    private String phone;
    private Address address;
    @Positive
    private BigDecimal balance;
}
