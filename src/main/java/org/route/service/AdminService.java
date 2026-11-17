package org.route.service;


import org.route.dto.OrdersOfCustomer;
import org.route.dto.RestaurantRevenuesResponse;
import org.route.dto.customer.CustomerResponse;
import org.route.dto.restaurant.MenuItemResponse;
import org.route.dto.restaurant.RestaurantResponse;
import org.route.dto.rider.RiderResponse;
import org.route.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.route.exception.ReportException;
import org.route.mapper.*;
import org.route.model.*;
import org.route.model.Items.MenuItem;
import org.route.model.Items.OrderItem;
import org.route.model.orderPromotion.Promotion;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.route.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final OrderInvoiceService invoiceService;
    private final OrderInvoiceRepository orderInvoiceRepository;
    private final OrderRepository  orderRepository;
    private final RestaurantRepository  restaurantRepository;
    private final RiderRepository  riderRepository;
    private final CustomerRepository  customerRepository;
    private final PromotionRepository promotionRepository;
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;
    private final RiderMapper riderMapper;
    private final MenuItemMapper menuItemMapper;
    private final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;


    public BigDecimal calculateTotalRevenue(LocalDate startDate, LocalDate endDate ){
        if(endDate.isBefore(startDate))
            throw new ReportException(HttpStatus.BAD_REQUEST, "End date is before start date");
        BigDecimal [] totalSum = {BigDecimal.valueOf(0)};
        orderInvoiceRepository.findAll().stream().
                filter(invoice -> invoice.getOrderDate().toLocalDate().isAfter(startDate)||
                        invoice.getOrderDate().toLocalDate().equals(startDate)).
                filter(invoice -> invoice.getOrderDate().toLocalDate().isBefore(endDate)||
                        invoice.getOrderDate().toLocalDate().equals(endDate)).
                forEach(invoice ->
                   totalSum[0]=totalSum[0].add(invoiceService.calculateTotal(invoice))
                 );
        return totalSum[0];
    }

    public RestaurantRevenuesResponse mapToRestaurantRevenuesResponse(Restaurant restaurant,BigDecimal totalRevenue){
        RestaurantRevenuesResponse response = new RestaurantRevenuesResponse();
        response.setRestaurantId(restaurant.getId());
        response.setRestaurantName(restaurant.getName());
        response.setRestaurantRevenues(totalRevenue);
        return  response;
    }

    public List<RestaurantRevenuesResponse> displayTopFiveRestaurants(int month, int year){
        if(month>12)
            throw new ReportException(HttpStatus.BAD_REQUEST, "Month must be between 1 and 12");
        if(year<2000||year>2026)
            throw new ReportException(HttpStatus.BAD_REQUEST, "Year must be between 2000 and 2026");
        Map<Integer,BigDecimal> restaurantsRevenues=new HashMap<>();
        orderRepository.findAll().stream().
                filter(order -> order.getPlacedAt().getMonthValue()== month).
                filter(order -> order.getPlacedAt().getYear()==year).
                filter(order -> order.getOrderStatus()!=OrderStatus.CANCELLED).
                forEach(order ->{
                if(restaurantsRevenues.containsKey(order.getRestaurant().getId())){
                    restaurantsRevenues.put(order.getRestaurant().getId(),
                            order.getTotalPrice().
                                    add(restaurantsRevenues.get(order.getRestaurant().getId())));
                }
                else
                    restaurantsRevenues.put(order.getRestaurant().getId(),order.getTotalPrice());
                });
        return restaurantsRevenues.entrySet().stream().
                sorted(Map.Entry.<Integer, BigDecimal>comparingByValue().reversed()).
                limit(5)
                .map(entry->
                        mapToRestaurantRevenuesResponse(
                                restaurantService.findRestaurantById(entry.getKey())
                                ,entry.getValue()))
                .toList();

    }

    public double calculateAverageOrder( String district){
        double avg= orderRepository.findAll().stream().
                filter(order -> order.getDeliveryAddress().getDistrict().equals(district)).
                mapToDouble(o->o.getTotalPrice().doubleValue()).
                average().orElse(0.0);
        if(avg==0.0)
            throw new ReportException(HttpStatus.BAD_REQUEST,"no orders in this district.");
        return avg;
    }

    public void addPromotion(Promotion promotion){
        promotionRepository.addPromotion(promotion);
    }

    public List<Promotion> getPromotions(){
        return promotionRepository.findAll();
    }

    public List<RestaurantResponse> filterRestaurants(double rate,int completedOrders){
       return restaurantRepository.findAll().stream().
                filter(restaurant -> restaurant.getRate()>rate).
                filter(restaurant -> restaurant.getCompletedOrders()>=completedOrders).
                map(restaurantMapper::toRestaurantResponse).
                toList();
    }

    public Map<OrderStatus,Long> countOrdersPerStatus(){
        return orderRepository.findAll().stream().
                map(Order::getOrderStatus).
                collect(Collectors.groupingBy(
                        Function.identity(),Collectors.counting()));
    }

    public List<RiderResponse> displayRidersDeliveries(){
       return riderRepository.findAllRiders().stream().map(riderMapper::toResponse).toList();
    }

    public MenuItemResponse displayFrequentlyMenuItem(){
        MenuItem menuItem= orderRepository.findAll().stream().
                flatMap(order -> order.getItems().values().stream()).
                map(OrderItem::getItem).
                collect(Collectors.groupingBy(
                        Function.identity(),Collectors.counting())).
                entrySet().
                stream().
                max(Map.Entry.comparingByValue()).
                map(Map.Entry::getKey).
                orElse(null);
        return menuItemMapper.toMenuItemResponse(menuItem);
    }

    public OrdersOfCustomer displayOrdersOfCustomer(int customerId){
        Comparator<Order> comparator=Comparator.comparing(Order::getPlacedAt).reversed();
        BigDecimal[] totalSpend={new BigDecimal(0)};
        OrdersOfCustomer ordersOfCustomer = new OrdersOfCustomer();
        orderRepository.findAll().stream().
                filter(order -> order.getCustomer().getId()==customerId).
                sorted(comparator).forEach(order->{
                    ordersOfCustomer.getOrders().add(orderMapper.toOrderResponse(order));
                    totalSpend[0]=totalSpend[0].add(order.getTotalPrice());
                });
        ordersOfCustomer.setTotalSpend(totalSpend[0]);
        return ordersOfCustomer;
    }

    public int findPeakHour(){
        return orderRepository.findAll().stream().
                map(order -> order.getPlacedAt().getHour()).
                collect(Collectors.groupingBy(
                        Function.identity(),Collectors.counting())).
                entrySet().
                stream().
                max(Map.Entry.comparingByValue()).
                map(Map.Entry::getKey).
                orElse(0);
    }

    public List<CustomerResponse> findNotActiveCustomers(){
       return customerRepository.findAll().stream()
               .filter(customer ->
                        customer.getLastOrder()==null||
                                ChronoUnit.DAYS.between(customer.getLastOrder(),LocalDateTime.now())>30)
               .map(customerMapper::toCustomerResponse).toList();

    }


}
