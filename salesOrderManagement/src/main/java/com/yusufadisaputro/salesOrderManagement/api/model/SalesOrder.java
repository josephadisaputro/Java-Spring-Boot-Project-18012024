package com.yusufadisaputro.salesOrderManagement.api.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "sales_orders")

public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "sales_order_number", unique = true, nullable = false)
    private String salesOrderNumber;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "sales_order_id")
    private List<ItemBought> items;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
        lastUpdatedDate = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedDate = new Date();
    }

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

