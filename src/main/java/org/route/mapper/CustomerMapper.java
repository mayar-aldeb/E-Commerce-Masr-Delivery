package org.route.mapper;

import org.mapstruct.Mapper;
import org.route.dto.customer.CustomerRequest;
import org.route.dto.customer.CustomerResponse;
import org.route.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequest customerRequest);
    CustomerResponse toCustomerResponse(Customer customer);
}
