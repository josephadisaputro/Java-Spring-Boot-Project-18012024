package com.yusufadisaputro.salesOrderManagement.api.model;

import com.yusufadisaputro.salesOrderManagement.api.model.*;

import java.util.Date;
import java.util.List;

public class SalesOrderResponse {
    private Integer id;
    private String salesOrderNumber;
    private Customer customer;
    private String deliveryAddress;
    private List<ItemBought> items;
    private boolean isDeleted;
    private Date createdDate;
    private Date lastUpdatedDate;

    // Constructor
    public SalesOrderResponse(SalesOrder salesOrder, Customer customer) {
        this.id = salesOrder.getId();
        this.salesOrderNumber = salesOrder.getSalesOrderNumber();
        this.customer = customer;
        this.deliveryAddress = salesOrder.getDeliveryAddress();
        this.items = salesOrder.getItems();
        this.isDeleted = salesOrder.getIsDeleted();
        this.createdDate = salesOrder.getCreatedDate();
        this.lastUpdatedDate = salesOrder.getLastUpdatedDate();
    }

    // Setters and Getters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<ItemBought> getItems() {
        return items;
    }

    public void setItems(List<ItemBought> items) {
        this.items = items;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}

