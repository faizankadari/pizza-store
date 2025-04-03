package com.example.pizzastore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.pizzastore.model.Pizza;
import com.example.pizzastore.repository.PizzaRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class PizzaService {

	@Autowired
	private PizzaRepository pizzaRepository;

	public Mono<Pizza> createPizza(Pizza pizza) {
		return pizzaRepository.save(pizza);
	}

	@Cacheable(value = "pizzas", key = "#id")
	public Mono<Pizza> getPizzaById(Integer id) {
		log.info("getting pizza by ID");
		return pizzaRepository.findById(id);
	}

	@Cacheable(value = "allPizzas")
	public Flux<Pizza> getAllPizzas() {

		log.info("grtting all pizza");
		return pizzaRepository.findAll();
	}

	public Flux<Pizza> getPizzaByName(String name) {
		return pizzaRepository.findByName(name);
	}

	@CachePut(value = "pizzas", key = "#pizza.id")
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

	@CacheEvict(value = "pizzas", key = "#id")
	public Mono<Void> deletePizza(Integer id) {
		return pizzaRepository.findById(id).flatMap(existingPizza -> pizzaRepository.deleteById(id));
	}
}
