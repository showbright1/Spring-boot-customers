package com.altimatez.springboot.customerService.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altimatez.springboot.customerService.model.Customer;
import com.altimatez.springboot.customerService.repo.CustomerRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	CustomerRepository repository;
	
	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		System.out.println("Get all customers");
		
		List<Customer> customers = new ArrayList<>();
		repository.findAll().forEach(customers::add);
		
		return customers;
	}
	
	@PostMapping("/customers/create")
	public Customer postUser(@RequestBody Customer customer) {
		Customer _customer = repository.save(new Customer(customer.getName(), customer.getAge()));
		return _customer;
	}
	
	@DeleteMapping("/customers/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") Long id) {
		System.out.println("Delete customer with id "+ id + " ...");
		
		repository.deleteById(id);
		
		return new ResponseEntity<String>("Customer has been deleted", HttpStatus.OK);
	}
	
	@DeleteMapping("/customers/delete")
	public ResponseEntity<String> deleteAllCustomers() {
		System.out.println("Delete all customers...");
		
		repository.deleteAll();
		
		return new ResponseEntity<String>("All customers has been deleted", HttpStatus.OK);
	}
	
	@GetMapping("customers/age/{age}")
	public List<Customer> findByAge(@PathVariable int age) {
		System.out.println("find customers with age " + age + " ...");
		
		List<Customer> _customers = repository.findByAge(age);
		
		return _customers;
	}
	
	@GetMapping("/customers/{id}")
	public Optional<Customer> findById(@PathVariable Long id) {
		System.out.println("find customer by id "+ id + " ...");
		
		Optional<Customer> _customer = repository.findById(id);
		
		return _customer;
	}
	
	@PutMapping("/customers/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
		System.out.println("Update customer with id " + id + " ...");
		
		Optional<Customer> customerData = repository.findById(id);
		
		if(customerData.isPresent()) {
			Customer _customer = customerData.get();
			_customer.setName(customer.getName());
			_customer.setAge(customer.getAge());
			_customer.setActive(customer.isActive());
			return new ResponseEntity<>(repository.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}