package com.tokyovending.TokyoVending.services;

import com.tokyovending.TokyoVending.exceptions.RecordNotFoundException;
import com.tokyovending.TokyoVending.models.Order;
import com.tokyovending.TokyoVending.models.Product;
import com.tokyovending.TokyoVending.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order with ID " + id + " not found."));
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order order) {
        Order existingOrder = getOrderById(id);
        existingOrder.setProducts(order.getProducts());
        existingOrder.setOrderDateTime(order.getOrderDateTime());
        existingOrder.setCompleted(order.isCompleted());
        return orderRepository.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public String getUserOrderHistoryAsCsv(String username) {
        List<Order> orders = orderRepository.findByUser_Username(username);
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("OrderID,Product\n");
        for (Order order : orders) {
            for(Product product : order.getProducts()) {
                csvContent.append(order.getId()).append(",");
                csvContent.append(product.getName()).append("\n");
            }
        }
        return csvContent.toString();
    }

    public String exportAllOrdersToCSV() {
        List<Order> orders = getAllOrders();
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("OrderID,Username,Product\n");
        for (Order order : orders) {
            for(Product product : order.getProducts()) {
                csvContent.append(order.getId()).append(",");
                csvContent.append(order.getUser().getUsername()).append(",");
                csvContent.append(product.getName()).append("\n");
            }
        }
        return csvContent.toString();
    }


}

