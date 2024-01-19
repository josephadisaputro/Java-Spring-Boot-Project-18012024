package com.yusufadisaputro.salesOrderManagement.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yusufadisaputro.salesOrderManagement.api.model.*;
import com.yusufadisaputro.salesOrderManagement.api.repository.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import com.google.gson.Gson;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository){
        this.salesOrderRepository = salesOrderRepository;
    }

    // public Optional<SalesOrder> getSalesOrder(int id){
    //     return salesOrderRepository.findById(id);
    // }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private ConcurrentHashMap<Integer, CompletableFuture<Customer>> customerFutures = new ConcurrentHashMap<>();

    public SalesOrderResponse getSalesOrder(int id){
        Optional<SalesOrder> salesOrderOptional = salesOrderRepository.findById(id);
        if(salesOrderOptional.isPresent()){
            SalesOrder salesOrder = salesOrderOptional.get();
            SalesOrderResponse salesOrderResponse = new SalesOrderResponse(salesOrder, null);
            CompletableFuture<Customer> customerFuture = new CompletableFuture<>();
            customerFutures.put(salesOrder.getCustomerId(), customerFuture);
            kafkaTemplate.send("RequestCustomerDetails", String.valueOf(salesOrder.getCustomerId()));
            try {
                Customer customer = customerFuture.get(5, TimeUnit.SECONDS); // wait for customer details for up to 5 seconds
                salesOrderResponse.setCustomer(customer);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
                // handle exception
            } finally {
                customerFutures.remove(salesOrder.getCustomerId());
            }
            return salesOrderResponse;
        }
        return null;
    }


    @KafkaListener(topics = "CustomerDetails", groupId = "group_id")
    public void addCustomerDetails(String customer) {
        Gson gson = new Gson();
        Customer customerObject = gson.fromJson(customer, Customer.class);
        CompletableFuture<Customer> customerFuture = customerFutures.get(customerObject.getId());
        if (customerFuture != null) {
            customerFuture.complete(customerObject); // complete the future when customer details are received
        }
    }

    public SalesOrder setNewSalesOrder(SalesOrder salesOrder){
        try {
            return salesOrderRepository.save(salesOrder);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "SalesOrder with the same sales order number already exists", ex);
        }
    }

    public Page<SalesOrder> getListOfSalesOrders(int page, int size, String sortByKey, Sort.Direction sortOrder) {
        return salesOrderRepository.findAll(
            PageRequest.of(page, size, Sort.by(sortOrder, sortByKey))
        );
    }

    public SalesOrderResponse getSalesOrderWithSalesOrderNumber(String salesOrderNumber) {
        SalesOrder salesOrder = salesOrderRepository.findBysalesOrderNumber(salesOrderNumber)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales Order with the given sales order number not found"));

        SalesOrderResponse salesOrderResponse = new SalesOrderResponse(salesOrder, null);
        CompletableFuture<Customer> customerFuture = new CompletableFuture<>();
        customerFutures.put(salesOrder.getCustomerId(), customerFuture);
        kafkaTemplate.send("RequestCustomerDetails", String.valueOf(salesOrder.getCustomerId()));
        try {
            Customer customer = customerFuture.get(5, TimeUnit.SECONDS); // wait for customer details for up to 5 seconds
            salesOrderResponse.setCustomer(customer);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
            // handle exception
        } finally {
            customerFutures.remove(salesOrder.getCustomerId());
        }
        return salesOrderResponse;
    }

    public SalesOrder updateSalesOrderData(SalesOrder salesOrder) {
        return salesOrderRepository.findById(salesOrder.getId())
            .map(existingSalesOrder -> {
                existingSalesOrder.setSalesOrderNumber(null);
                existingSalesOrder.setCustomerId(null);
                existingSalesOrder.setDeliveryAddress(null);
                existingSalesOrder.setItems(null);
                return salesOrderRepository.save(existingSalesOrder);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales Order with the given id not found"));
    }

    public SalesOrder updateSalesOrderAsDeleted(Integer id) {
        return salesOrderRepository.findById(id)
            .map(existingSalesOrder -> {
                existingSalesOrder.setIsDeleted(true);
                return salesOrderRepository.save(existingSalesOrder);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales Order with the given id not found"));
    }
}
