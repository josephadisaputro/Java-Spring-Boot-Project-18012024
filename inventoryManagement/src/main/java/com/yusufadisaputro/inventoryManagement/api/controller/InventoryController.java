package com.yusufadisaputro.inventoryManagement.api.controller;

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

import com.yusufadisaputro.inventoryManagement.api.model.Inventory;
import com.yusufadisaputro.inventoryManagement.api.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

@RestController
public class InventoryController {

    private final InventoryService userService;

    @Autowired
    public InventoryController(InventoryService userService){
        this.userService = userService;
    }

    @PostMapping("/api/v1/inventory")
    public ResponseEntity<?> setUser(@RequestBody Inventory inventory){
        try {
            Inventory savedNewinventory = userService.setNewInventory(inventory);
            return ResponseEntity.ok(savedNewinventory);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/inventories")
    public ResponseEntity<?> getInventories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortByKey,
        @RequestParam(defaultValue = "ASC") Sort.Direction sortOrder
    ) {
        try {
            Page<Inventory> inventories = userService.getListOfInventories(page, size, sortByKey, sortOrder);
            return ResponseEntity.ok(inventories);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/inventory/phone/{phoneNumber}")
    public ResponseEntity<?> getinventoryWithPhone(@PathVariable String phoneNumber) {
        try {
            Inventory inventory = userService.getInventoryWithSKU(phoneNumber);
            return ResponseEntity.ok(inventory);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @GetMapping("/api/v1/inventory/{id}")
    public ResponseEntity<?> getinventoryDetails(@PathVariable("id") String id){
        int idNumber = Integer.parseInt(id);
        Optional<Inventory> user = userService.getInventory(idNumber);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            Map<String, String> error = new HashMap<>();
            error.put("error", "inventory not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/api/v1/inventory")
    public ResponseEntity<?> updateinventory(@RequestBody Inventory inventory){
        try {
            Inventory updatedinventory = userService.updateInventoryData(inventory);
            return ResponseEntity.ok(updatedinventory);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

    @DeleteMapping("/api/v1/inventory/{id}")
    public ResponseEntity<?> deleteinventory(@PathVariable Integer id){
        try {
            Inventory deletedinventory = userService.updateInventoryAsDeleted(id);
            return ResponseEntity.ok(deletedinventory);
        } catch (ResponseStatusException ex) {
            Map<String, String> error = new HashMap<>();
            error.put("error", ex.getReason());
            return new ResponseEntity<>(error, ex.getStatusCode());
        }
    }

}
