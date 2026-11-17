package org.route.validation;

import org.route.exception.CustomerException;
import org.route.model.Customer;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

public class CustomerValidation {


    public static void validateBalance(Customer customer, BigDecimal invoiceBalance){
        if(customer.getBalance().compareTo(invoiceBalance)<0)
            throw new CustomerException(HttpStatus.BAD_REQUEST,"Insufficient Balance.");
    }

}
