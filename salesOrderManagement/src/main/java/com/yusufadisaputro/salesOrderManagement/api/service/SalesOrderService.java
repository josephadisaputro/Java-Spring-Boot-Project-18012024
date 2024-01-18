package com.yusufadisaputro.salesOrderManagement.api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yusufadisaputro.salesOrderManagement.api.model.*;
import com.yusufadisaputro.salesOrderManagement.api.repository.*;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Service
public class SalesOrderService {

    private final SalesOrderRepository salesOrderRepository;

    public SalesOrderService(SalesOrderRepository salesOrderRepository){
        this.salesOrderRepository = salesOrderRepository;
    }

    public Optional<SalesOrder> getSalesOrder(int id){
        return salesOrderRepository.findById(id);
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

    public SalesOrder getSalesOrderWithSalesOrderNumber(String salesOrderNumber) {
        return salesOrderRepository.findBysalesOrderNumber(salesOrderNumber)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sales Order with the given sales order number not found"));
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
