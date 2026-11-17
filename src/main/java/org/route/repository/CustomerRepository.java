package org.route.repository;


import jakarta.annotation.PostConstruct;
import org.route.model.Address;
import org.route.model.Customer;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomerRepository {

   private final Map<Integer, Customer> customers=new HashMap<>();
   @PostConstruct
   public void init(){
       customers.put(1,
               new Customer("mayar",
                       "01156421168",
                       new Address("nasr city","ebvgbeg"),
                       BigDecimal.valueOf(2000)));
   }

   public List<Customer> findAll()
    {
        return customers.values().stream().toList();
    }

    public Optional<Customer> findById(int id)
    {
        return Optional.ofNullable(customers.get(id));
    }

    public void save(Customer customer)
    {
        customers.put(customer.getId(), customer);
    }







}
