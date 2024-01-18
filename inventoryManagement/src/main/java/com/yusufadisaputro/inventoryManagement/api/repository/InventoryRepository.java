package com.yusufadisaputro.inventoryManagement.api.repository;

import com.yusufadisaputro.inventoryManagement.api.model.Inventory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Optional<Inventory> findBysku(String sku);
}
