package com.yusufadisaputro.customerManagement.api.repository;

import com.yusufadisaputro.customerManagement.api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
