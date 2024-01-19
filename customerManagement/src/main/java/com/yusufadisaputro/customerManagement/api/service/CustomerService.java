package com.yusufadisaputro.customerManagement.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yusufadisaputro.customerManagement.api.model.Customer;
import com.yusufadisaputro.customerManagement.api.repository.CustomerRepository;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "RequestCustomerDetails", groupId = "group_id")
    public void consume(String id) {
        Optional<Customer> customerOptional = customerRepository.findById(Integer.parseInt(id));
        sendCustomerDetails(customerOptional);
    }

    public void sendCustomerDetails(Optional<Customer> customerOptional){
        if(customerOptional.isPresent()){
            Customer customer = customerOptional.get();
            Gson gson = new Gson();
            String jsonString = gson.toJson(customer);
            kafkaTemplate.send("CustomerDetails", jsonString);
        }
    }

    public Optional<Customer> getCustomer(int id){
        return customerRepository.findById(id);
    }

    public Customer setNewCustomer(Customer customer){
        try {
            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Customer with the same phone number already exists", ex);
        }
    }

    public Page<Customer> getListOfCustomers(int page, int size, String sortByKey, Sort.Direction sortOrder) {
        return customerRepository.findAll(
            PageRequest.of(page, size, Sort.by(sortOrder, sortByKey))
        );
    }

    public List<Customer> getListOfCustomersWithSimilarEmailAddress(String email) {
        return customerRepository.findByEmailContaining(email);
    }

    public Customer getCustomerWithPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with the given phone number not found"));
    }

    public Customer updateCustomerData(Customer customer) {
        return customerRepository.findById(customer.getId())
            .map(existingCustomer -> {
                existingCustomer.setEmail(customer.getEmail());
                existingCustomer.setPhoneNumber(customer.getPhoneNumber());
                existingCustomer.setFirstName(customer.getFirstName());
                existingCustomer.setLastName(customer.getLastName());
                existingCustomer.setDateOfBirth(customer.getDateOfBirth());
                existingCustomer.setMonthOfBirth(customer.getMonthOfBirth());
                existingCustomer.setYearOfBirth(customer.getYearOfBirth());
                return customerRepository.save(existingCustomer);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with the given id not found"));
    }

    public Customer updateCustomerAsDeleted(Integer id) {
        return customerRepository.findById(id)
            .map(existingCustomer -> {
                existingCustomer.setIsDeleted(true);
                return customerRepository.save(existingCustomer);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with the given id not found"));
    }
}
