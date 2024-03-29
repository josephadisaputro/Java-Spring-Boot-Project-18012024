package com.yusufadisaputro.salesOrderManagement.api.controller;

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

import com.yusufadisaputro.salesOrderManagement.api.model.*;
import com.yusufadisaputro.salesOrderManagement.api.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@RestController
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    @Autowired
    public SalesOrderController(SalesOrderService salesOrderService){
        this.salesOrderService = salesOrderService;
    }

    @PostMapping("/api/v1/sales-order")
    public ResponseEntity<?> setUser(@RequestBody SalesOrder salesOrder){
        try {
            SalesOrder savedNewsalesOrder = salesOrderService.setNewSalesOrder(salesOrder);
            return ResponseEntity.ok(savedNewsalesOrder);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/sales-orders")
    public ResponseEntity<?> getsalesOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortByKey,
        @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder
    ) {
        try {
            Page<SalesOrder> salesOrders = salesOrderService.getListOfSalesOrders(page, size, sortByKey, sortOrder);
            return ResponseEntity.ok(salesOrders);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/sales-order/number/{salesOrderNumber}")
    public ResponseEntity<?> getsalesOrderWithSalesOrderNumber(@PathVariable String salesOrderNumber) {
        try {
            SalesOrderResponse salesOrderResponse = salesOrderService.getSalesOrderWithSalesOrderNumber(salesOrderNumber);
            return ResponseEntity.ok(salesOrderResponse);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/sales-order/{id}")
    public ResponseEntity<?> getsalesOrderDetails(@PathVariable("id") String id){
        int idNumber = Integer.parseInt(id);
        SalesOrderResponse salesOrderResponse = salesOrderService.getSalesOrder(idNumber);
        return ResponseEntity.ok(salesOrderResponse);
        // if (user.isPresent()) {
        //     return ResponseEntity.ok(salesOrderResponse);
        // } else {
        //     Map<String, String> error = new HashMap<>();
        //     error.put("error", "salesOrder not found");
        //     return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        // }
    }

    @PutMapping("/api/v1/sales-order")
    public ResponseEntity<?> updatesalesOrder(@RequestBody SalesOrder salesOrder){
        try {
            SalesOrder updatedsalesOrder = salesOrderService.updateSalesOrderData(salesOrder);
            return ResponseEntity.ok(updatedsalesOrder);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @DeleteMapping("/api/v1/sales-order/{id}")
    public ResponseEntity<?> deletesalesOrder(@PathVariable Integer id){
        try {
            SalesOrder deletedsalesOrder = salesOrderService.updateSalesOrderAsDeleted(id);
            return ResponseEntity.ok(deletedsalesOrder);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

}
