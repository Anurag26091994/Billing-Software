package com.custom.Billing.Software.controller;

import com.custom.Billing.Software.constant.EventType;
import com.custom.Billing.Software.dto.ProductRequestDTO;
import com.custom.Billing.Software.dto.ProductResponseDTO;
import com.custom.Billing.Software.entity.ProductInventory;
import com.custom.Billing.Software.exception.ProductAlreadyExistsException;
import com.custom.Billing.Software.service.ProductInventoryService;
import com.custom.Billing.Software.util.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductInventoryController {

    private final ProductInventoryService service;

    @PostMapping("/product/create")
    public ResponseEntity<ProductRequestDTO> create(@RequestBody ProductRequestDTO dto) throws ProductAlreadyExistsException {
        return ResponseEntity.ok(service.createProduct(dto));
    }

    @PostMapping("/product/getAll")
    public ResponseEntity<List<ProductRequestDTO>> getAll() {
        return ResponseEntity.ok(service.getAllProducts());
    }

    @PostMapping("/product/getByProductName/{productName}")
    public ResponseEntity<List<ProductRequestDTO>> getByProductName(@PathVariable String productName) {
        return ResponseEntity.ok(service.getByProductName(productName));
    }

    @PutMapping("/product/updateById/{id}")
    public ResponseEntity<ProductRequestDTO> update(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(service.updateProduct(id, dto));
    }

    @DeleteMapping("/product/deleteById/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
