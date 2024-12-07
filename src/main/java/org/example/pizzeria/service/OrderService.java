package org.example.pizzeria.service;

import org.example.pizzeria.dto.CreateOrderRequest;
import org.example.pizzeria.entity.Order;
import org.example.pizzeria.entity.OrderItem;
import org.example.pizzeria.entity.Pizza;
import org.example.pizzeria.entity.User;
import org.example.pizzeria.repository.OrderItemRepository;
import org.example.pizzeria.repository.OrderRepository;
import org.example.pizzeria.repository.PizzaRepository;
import org.example.pizzeria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private UserRepository userRepository;

    public Order createOrder(CreateOrderRequest req, User user) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setStatus("NEW");
        order.setCustomerName(req.getCustomerName());
        order.setCustomerPhone(req.getCustomerPhone());
        order.setDeliveryAddress(req.getDeliveryAddress());
        order.setUser(user);
        order = orderRepository.save(order);

        double total = 0;
        for (var itemReq : req.getItems()) {
            Pizza pizza = pizzaRepository.findById(itemReq.getPizzaId()).orElse(null);
            if (pizza != null) {
                OrderItem oi = new OrderItem();
                oi.setOrder(order);
                oi.setPizza(pizza);
                oi.setQuantity(itemReq.getQuantity());
                oi.setSelectedSize(itemReq.getSelectedSize());
                oi.setSelectedDough(itemReq.getSelectedDough());
                double unitPrice = pizza.getBasePrice(); // add logic for different sizes if needed
                oi.setUnitPrice(unitPrice);
                oi.setTotalItemPrice(unitPrice * itemReq.getQuantity());
                total += oi.getTotalItemPrice();
                orderItemRepository.save(oi);
            }
        }

        order.setTotalPrice(total);
        return orderRepository.save(order);
    }
}