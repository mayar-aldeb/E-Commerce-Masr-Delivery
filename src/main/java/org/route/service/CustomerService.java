package org.route.service;

import org.route.dto.customer.CustomerRequest;
import org.route.dto.customer.CustomerResponse;
import org.route.enums.CustomerLoyaltyTier;
import org.route.exception.CustomerException;
import lombok.RequiredArgsConstructor;
import org.route.mapper.CustomerMapper;
import org.route.model.Customer;
import org.route.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.route.repository.CustomerRepository;
import org.route.validation.CustomerValidation;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public void updateLoyaltyTier(Customer customer){
        if(customer.getCompletedOrders()>30)
            customer.setLoyaltyTier(CustomerLoyaltyTier.GOLD);
        else if(customer.getCompletedOrders()>9)
            customer.setLoyaltyTier(CustomerLoyaltyTier.SILVER);
    }

    public void addCustomer(CustomerRequest customerRequest){
        Customer customer=customerMapper.toCustomer(customerRequest);
        customer.setId(Customer.createCount());
        customerRepository.save(customer);
    }

    public List<CustomerResponse> findAllCustomers(){
        return customerRepository.findAll().stream().map(customerMapper::toCustomerResponse).toList();
    }

    public void payFromWallet(Customer customer,BigDecimal invoiceBalance){
        CustomerValidation.validateBalance(customer,invoiceBalance);
        customer.setBalance(customer.getBalance().subtract(invoiceBalance));
    }

    public void refundToWallet(Customer customer,BigDecimal balance){
        customer.setBalance(customer.getBalance().add(balance));
    }

    public void setCompletedOrderToCustomer(Customer customer,Order order){
        customer.setCompletedOrders(customer.getCompletedOrders()+1);
        customer.setLastOrder(order.getPlacedAt());
        updateLoyaltyTier(customer);
    }

    public Customer findCustomerById(int customerId){
        return customerRepository.findById(customerId).orElseThrow(()->new CustomerException(HttpStatus.NOT_FOUND,"Customer not found"));
    }


}
