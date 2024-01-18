package com.yusufadisaputro.customerManagement.api.repository;

import com.yusufadisaputro.customerManagement.api.model.Customer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findByEmailContaining(String email);

    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
