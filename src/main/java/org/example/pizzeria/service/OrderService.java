// OrderService.java - повертає DTO замість сутностей
package org.example.pizzeria.service;

import org.example.pizzeria.dto.CreateOrderRequest;
import org.example.pizzeria.dto.OrderResponse;
import org.example.pizzeria.dto.OrderItemResponse;
import org.example.pizzeria.entity.*;
import org.example.pizzeria.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private OrderItemRepository orderItemRepository;
    @Autowired private PizzaRepository pizzaRepository;

    public OrderResponse createOrder(CreateOrderRequest req, User user) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus("NEW");
        order.setCustomerName(req.getCustomerName());
        order.setCustomerPhone(req.getCustomerPhone());
        order.setDeliveryAddress(req.getDeliveryAddress());
        order.setUser(user);
        order.setItems(new ArrayList<>()); // Инициализация списка
        order = orderRepository.save(order);

        double total = 0;
        for (var itemReq : req.getItems()) {
            Pizza pizza = pizzaRepository.findById(itemReq.getPizzaId())
                    .orElseThrow(() -> new RuntimeException("Pizza not found"));
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setPizza(pizza);
            oi.setQuantity(itemReq.getQuantity());
            oi.setSelectedSize(itemReq.getSelectedSize());
            oi.setSelectedDough(itemReq.getSelectedDough());
            double unitPrice = pizza.getBasePrice();
            oi.setUnitPrice(unitPrice);
            oi.setTotalItemPrice(unitPrice * itemReq.getQuantity());
            total += oi.getTotalItemPrice();
            orderItemRepository.save(oi);
            order.getItems().add(oi); // Добавление в список
        }

        order.setTotalPrice(total);
        order = orderRepository.save(order);

        return toOrderResponse(order);
    }

    public OrderResponse getOrderById(Long orderId, User user) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        // Перевірка прав доступу: якщо не адмін і не власник замовлення - помилка
        if (!user.getRole().equals("ADMIN") && !o.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        return toOrderResponse(o);
    }

    public List<OrderResponse> getMyOrders(User user) {
        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse cancelOrder(Long orderId, User user) {
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        if (!o.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        if (!"NEW".equals(o.getStatus())) {
            throw new RuntimeException("Cannot cancel order that is not NEW");
        }
        o.setStatus("CANCELLED");
        orderRepository.save(o);
        return toOrderResponse(o);
    }

    public OrderResponse updateOrderStatus(Long orderId, String status, User admin) {
        if (!"ADMIN".equals(admin.getRole())) {
            throw new RuntimeException("Access denied");
        }
        Order o = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        o.setStatus(status);
        orderRepository.save(o);
        return toOrderResponse(o);
    }

    private OrderResponse toOrderResponse(Order o) {
        OrderResponse resp = new OrderResponse();
        resp.setOrderId(o.getId());
        resp.setOrderDate(o.getOrderDate());
        resp.setStatus(o.getStatus());
        resp.setCustomerName(o.getCustomerName());
        resp.setCustomerPhone(o.getCustomerPhone());
        resp.setDeliveryAddress(o.getDeliveryAddress());
        resp.setTotalPrice(o.getTotalPrice());
        resp.setItems(o.getItems().stream().map(oi -> {
            OrderItemResponse i = new OrderItemResponse();
            i.setPizzaId(oi.getPizza().getId());
            i.setQuantity(oi.getQuantity());
            i.setSelectedSize(oi.getSelectedSize());
            i.setSelectedDough(oi.getSelectedDough());
            i.setUnitPrice(oi.getUnitPrice());
            i.setTotalItemPrice(oi.getTotalItemPrice());
            return i;
        }).collect(Collectors.toList()));
        return resp;
    }
}
