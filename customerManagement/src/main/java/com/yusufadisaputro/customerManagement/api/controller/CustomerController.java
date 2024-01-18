package com.yusufadisaputro.customerManagement.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.yusufadisaputro.customerManagement.api.model.Customer;
import com.yusufadisaputro.customerManagement.api.service.CustomerService;

@RestController
public class CustomerController {

    private final CustomerService userService;

    @Autowired
    public CustomerController(CustomerService userService){
        this.userService = userService;
    }
    
    @GetMapping("/api/v1/customer")
    public ResponseEntity<?> getCustomerDetails(@RequestParam("id") String id){
        int idNumber = Integer.parseInt(id);
        Optional<Customer> user = userService.getUser(idNumber);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/v1/customer")
    public ResponseEntity<?> setUser(@RequestBody Customer customer){
        try {
            Customer savedNewCustomer = userService.setNewCustomer(customer);
            return ResponseEntity.ok(savedNewCustomer);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }
}
