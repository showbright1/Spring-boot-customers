package com.altimatez.springboot.customerService.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altimatez.springboot.customerService.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	List<Customer> findByAge(int age);
}
