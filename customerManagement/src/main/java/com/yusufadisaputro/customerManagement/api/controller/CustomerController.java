package com.yusufadisaputro.customerManagement.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.yusufadisaputro.customerManagement.api.model.Customer;
import com.yusufadisaputro.customerManagement.api.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@RestController
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/api/v1/customer")
    public ResponseEntity<?> setUser(@RequestBody Customer customer){
        try {
            Customer savedNewCustomer = customerService.setNewCustomer(customer);
            return ResponseEntity.ok(savedNewCustomer);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/customers")
    public ResponseEntity<?> getCustomers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortByKey,
        @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder
    ) {
        try {
            Page<Customer> customers = customerService.getListOfCustomers(page, size, sortByKey, sortOrder);
            return ResponseEntity.ok(customers);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/customers/{email}")
    public ResponseEntity<?> getCustomersWithEmail(@PathVariable String email) {
        try {
            List<Customer> customers = customerService.getListOfCustomersWithSimilarEmailAddress(email);
            return ResponseEntity.ok(customers);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/customer/phone/{phoneNumber}")
    public ResponseEntity<?> getCustomerWithPhone(@PathVariable String phoneNumber) {
        try {
            Customer customer = customerService.getCustomerWithPhoneNumber(phoneNumber);
            return ResponseEntity.ok(customer);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/customer/{id}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable("id") String id){
        int idNumber = Integer.parseInt(id);
        Optional<Customer> user = customerService.getCustomer(idNumber);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Customer not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/v1/customer")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer){
        try {
            Customer updatedCustomer = customerService.updateCustomerData(customer);
            return ResponseEntity.ok(updatedCustomer);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @DeleteMapping("/api/v1/customer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Integer id){
        try {
            Customer deletedCustomer = customerService.updateCustomerAsDeleted(id);
            return ResponseEntity.ok(deletedCustomer);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

}
