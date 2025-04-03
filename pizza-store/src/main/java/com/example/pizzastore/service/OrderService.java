package com.example.pizzastore.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pizzastore.model.Order;
import com.example.pizzastore.model.OrderDetails;
import com.example.pizzastore.repository.OrderDetailsRepository;
import com.example.pizzastore.repository.OrderRepository;
import com.example.pizzastore.repository.PizzaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private PizzaRepository pizzaRepository;

	OrderService(OrderDetailsRepository orderDetailsRepository) {
		this.orderDetailsRepository = orderDetailsRepository;
	}

	public Mono<OrderDetails> createOrder(Order order) {
		// Validate each pizza ID in the order and fetch corresponding Pizza objects
		return Flux.fromIterable(order.getPizzasID()).flatMap(pizzaRepository::findById).collectList()
				.flatMap(pizzas -> {
					if (pizzas.size() != order.getPizzasID().size()) {
						return Mono.error(new RuntimeException("One or more pizzas not found"));
					}
					return orderRepository.save(order).flatMap(saved -> {
						// Create OrderDetails object
						OrderDetails orderDetails = new OrderDetails();
						orderDetails.setOrderDetailId(order.getOrderId()); // Map Order ID to OrderDetails ID
						orderDetails.setPizzas(pizzas); // Set list of valid Pizza objects
						orderDetails.setPizzasID(order.getPizzasID()); // Set list of pizza IDs
						orderDetails.setStatus("Preparing"); // Initial status
						orderDetails.setTimestamp(LocalDateTime.now()); // Current timestamp

						return orderDetailsRepository.save(orderDetails); // Save and return OrderDetails
					});
				});
	}

	public Mono<OrderDetails> getOrderById(String id) {
		return orderDetailsRepository.findById(id);
	}

	public Flux<OrderDetails> getAllOrders() {
		return orderDetailsRepository.findAll();
	}

	public Mono<OrderDetails> updateOrderStatus(String id) {
		return orderDetailsRepository.findById(id).flatMap(order -> {
			order.setStatus("Delivered");
			return orderDetailsRepository.save(order);
		});
	}

	public Mono<Void> cancelOrder(String id) {
		return orderRepository.findById(id).flatMap(existingOrder -> orderRepository.deleteById(id))
				.then(orderDetailsRepository.findById(id).flatMap(existingOrderDetails -> orderDetailsRepository
						.deleteById(existingOrderDetails.getOrderDetailId())));

	}
}
