package com.yusufadisaputro.inventoryManagement.api.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.yusufadisaputro.inventoryManagement.api.model.Inventory;
import com.yusufadisaputro.inventoryManagement.api.repository.InventoryRepository;
import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    public Optional<Inventory> getInventory(int id){
        return inventoryRepository.findById(id);
    }

    public Inventory setNewInventory(Inventory inventory){
        try {
            return inventoryRepository.save(inventory);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Inventory with the same sku already exists", ex);
        }
    }

    public Page<Inventory> getListOfInventories(int page, int size, String sortByKey, Sort.Direction sortOrder) {
        return inventoryRepository.findAll(
            PageRequest.of(page, size, Sort.by(sortOrder, sortByKey))
        );
    }

    public Inventory getInventoryWithSKU(String sku) {
        return inventoryRepository.findBysku(sku)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with the given sku not found"));
    }

    public Inventory updateInventoryData(Inventory inventory) {
        return inventoryRepository.findById(inventory.getId())
            .map(existingInventory -> {
                existingInventory.setSku(inventory.getSku());
                existingInventory.setProductName(inventory.getProductName());
                existingInventory.setSellPrice(inventory.getSellPrice());
                existingInventory.setBuyPrice(inventory.getBuyPrice());
                existingInventory.setStockQuantity(inventory.getStockQuantity());
                existingInventory.setStockUnit(inventory.getStockUnit());
                return inventoryRepository.save(existingInventory);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory with the given id not found"));
    }

    public Inventory updateInventoryAsDeleted(Integer id) {
        return inventoryRepository.findById(id)
            .map(existingInventory -> {
                existingInventory.setIsDeleted(true);
                return inventoryRepository.save(existingInventory);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory with the given id not found"));
    }
}
