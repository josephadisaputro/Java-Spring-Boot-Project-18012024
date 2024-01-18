package com.yusufadisaputro.salesOrderManagement.api.repository;

import com.yusufadisaputro.salesOrderManagement.api.model.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {

    Optional<SalesOrder> findBysalesOrderNumber(String salesOrderNumber);
}
