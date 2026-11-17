package org.route.dto.customer;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.route.model.Address;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerResponse {

    private String name;
    private String phone;
    private List<Address> addresses;
    private BigDecimal balance;
}
