package com.yusufadisaputro.salesOrderManagement.api.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "sales_order_items")
public class ItemBought {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    @Column(name = "sku")
    private String sku;

    @Column(name = "sell_price")
    private Double sellPrice;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @PrePersist
    protected void onCreate() {
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

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}


