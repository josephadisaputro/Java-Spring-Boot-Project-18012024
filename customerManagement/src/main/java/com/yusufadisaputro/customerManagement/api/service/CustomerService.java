package com.yusufadisaputro.customerManagement.api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yusufadisaputro.customerManagement.api.model.Customer;
import com.yusufadisaputro.customerManagement.api.repository.CustomerRepository;
import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> getUser(int id){
        return customerRepository.findById(id);
    }

    public Customer setNewCustomer(Customer customer){
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer with the same phone number already exists", ex);
        }
    }
}
