package org.route.repository;

import org.route.model.Order;
import org.route.model.OrderInvoice;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderInvoiceRepository {
 private final Map<Order, OrderInvoice> invoices=new HashMap<>();

 public List<OrderInvoice> findAll() {
     return invoices.values().stream().toList();
 }

 public Optional<OrderInvoice> findById(Order order) {
     return Optional.ofNullable(invoices.get(order));
 }


 public void save(OrderInvoice orderInvoice) {
     invoices.put(orderInvoice.getOrder(), orderInvoice);
 }
}
