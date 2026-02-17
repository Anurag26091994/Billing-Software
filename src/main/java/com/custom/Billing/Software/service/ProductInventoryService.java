package com.custom.Billing.Software.service;

import com.custom.Billing.Software.constant.EventType;
import com.custom.Billing.Software.dto.ProductRequestDTO;
import com.custom.Billing.Software.dto.ProductResponseDTO;
import com.custom.Billing.Software.exception.ProductAlreadyExistsException;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductInventoryService {
    
    @Nullable ProductRequestDTO createProduct(ProductRequestDTO dto) throws ProductAlreadyExistsException;

    @Nullable List<ProductRequestDTO> getAllProducts();

    @Nullable ProductRequestDTO updateProduct(Long id, ProductRequestDTO dto);

    void deleteProduct(Long id);

    @Nullable List<ProductRequestDTO> getByProductName(String productName);
}
