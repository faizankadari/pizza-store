package com.example.pizzastore.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document(collection = "orders")
@Data
public class Order {
	@Id
	private String orderId;

	@NotEmpty(message = "Order must contain at least one pizza")
	private List<Integer> pizzasIds;

	public List<Integer> getPizzasIds() {
		return pizzasIds;
	}

	public void setPizzasIds(List<Integer> pizzasIds) {
		this.pizzasIds = pizzasIds;
	}

	@NotBlank(message = "Order status must not be blank")
	private String status; // e.g., "Preparing", "Delivered"

	@NotNull
	private LocalDateTime timestamp = LocalDateTime.now();

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

}
