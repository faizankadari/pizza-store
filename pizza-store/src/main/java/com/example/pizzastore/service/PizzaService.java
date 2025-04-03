package com.example.pizzastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pizzastore.model.Pizza;
import com.example.pizzastore.repository.PizzaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

	public Mono<Pizza> createPizza(Pizza pizza) {
		return pizzaRepository.save(pizza);
	}

	public Mono<Pizza> getPizzaById(Integer id) {
		return pizzaRepository.findById(id);
	}

	public Flux<Pizza> getAllPizzas() {
		return pizzaRepository.findAll();
	}

	public Flux<Pizza> getPizzaByName(String name) {
		return pizzaRepository.findByName(name);
	}

	public Mono<Pizza> updatePizza(Integer id, Pizza pizza) {
		return pizzaRepository.findById(id).flatMap(existingPizza -> {
			pizza.setId(pizza.getId());
			pizza.setName(pizza.getName());
			pizza.setPrice(pizza.getPrice());
			pizza.setSize(pizza.getSize());
			pizza.setDescription(pizza.getDescription());
			pizza.setToppings(pizza.getToppings());
			return pizzaRepository.save(pizza);
		});
	}

	public Mono<Void> deletePizza(Integer id) {
		return pizzaRepository.findById(id).flatMap(existingPizza -> pizzaRepository.deleteById(id));
	}
}
