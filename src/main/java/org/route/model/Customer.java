package org.route.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.route.enums.CustomerLoyaltyTier;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Customer {
    private static int count=0;
    private int id;
    @NotBlank
    private String name;
    @Pattern(regexp = "01[0125]\\d{8}",message = "invalid phone number.")
    private String phone;
    private final List<Address> addresses=new ArrayList<>();
    @Positive
    private BigDecimal balance;
    private int completedOrders;
    private CustomerLoyaltyTier loyaltyTier;
    private LocalDateTime lastOrder;


    public Customer(String name, String phone,Address address, BigDecimal balance) {
        this.name = name;
        this.phone = phone;
        this.addresses.add(address);
        this.lastOrder=null;
        this.balance = balance;
        this.loyaltyTier=CustomerLoyaltyTier.BRONZE;
    }

   public static int createCount(){
        return ++count;
    }


}
