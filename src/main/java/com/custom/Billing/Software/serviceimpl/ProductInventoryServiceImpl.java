package com.custom.Billing.Software.serviceimpl;

import com.custom.Billing.Software.constant.EventType;
import com.custom.Billing.Software.dto.ProductRequestDTO;
import com.custom.Billing.Software.dto.ProductResponseDTO;
import com.custom.Billing.Software.entity.ProductInventory;
import com.custom.Billing.Software.exception.ProductAlreadyExistsException;
import com.custom.Billing.Software.exception.ProductNotFoundException;
import com.custom.Billing.Software.repository.ProductInventoryRepository;
import com.custom.Billing.Software.service.ProductInventoryService;
import com.custom.Billing.Software.util.FormLogger;
import com.custom.Billing.Software.util.ProductValidator;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private ProductInventoryRepository productInventoryRepository;

    public ProductInventoryServiceImpl(ProductInventoryRepository productInventoryRepository, ModelMapper modelMapper) {
        this.productInventoryRepository = productInventoryRepository;
        this.modelMapper = modelMapper;
    }

    private ModelMapper modelMapper;

    // CREATE
    @Override
    public ProductRequestDTO createProduct(ProductRequestDTO dto) throws ProductAlreadyExistsException {

        FormLogger.info("Creating product: {}", dto.getProductName());

        try {

            ProductValidator.validate(dto);

            // Check duplicate
            productInventoryRepository
                    .findByProductNameIgnoreCase(dto.getProductName())
                    .ifPresent(existingProduct -> {

                        List<String> suggestions =
                                productInventoryRepository
                                        .findByProductNameContainingIgnoreCase(dto.getProductName())
                                        .stream()
                                        .map(ProductInventory::getProductName)
                                        .collect(Collectors.toList());

                        try {
                            throw new ProductAlreadyExistsException(
                                    "Product already exists with name: "
                                            + dto.getProductName()
                                            + ". Suggestions: "
                                            + suggestions);
                        } catch (ProductAlreadyExistsException e) {
                            throw new RuntimeException(e);
                        }
                    });

            ProductInventory product = mapToEntity(dto);

            ProductInventory saved = productInventoryRepository.save(product);

            FormLogger.info("Product saved with id {}", saved.getProductId());

            return mapToDTO(saved);

        } catch (Exception e) {

            FormLogger.error("Error while creating product: {}", e.getMessage());
            throw new RuntimeException("Unable to create product. Please try again.");
        }
    }



    // READ
    @Override
    public List<ProductRequestDTO> getAllProducts() {
        return productInventoryRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Override
    public ProductRequestDTO updateProduct(Long id, ProductRequestDTO dto) {

        ProductInventory existing = productInventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setProductName(dto.getProductName());
        existing.setBuyPrice(dto.getBuyPrice());
        existing.setSellingPrice(dto.getSellingPrice());
        existing.setCategory(dto.getCategory());
        existing.setType(dto.getType());

        return mapToDTO(productInventoryRepository.save(existing));
    }

    // DELETE
    public void deleteProduct(Long id) {
        if (!productInventoryRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productInventoryRepository.deleteById(id);
    }

    @Override
    public @Nullable List<ProductRequestDTO> getByProductName(String productName) {
        FormLogger.info("Searching product by productName: {}", productName);

        try {

            if (productName == null || productName.trim().isEmpty()) {
                throw new IllegalArgumentException("Search productName cannot be empty");
            }

            List<ProductInventory> products =
                    productInventoryRepository.searchByproductName(productName.trim());

            if (products.isEmpty()) {
                throw new ProductNotFoundException(
                        "No product found matching: " + productName);
            }

            return products.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

        } catch (ProductNotFoundException e) {
            FormLogger.error("Search error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            FormLogger.error("Unexpected error during search: {}", e.getMessage());
            throw new RuntimeException("Error while searching product");
        }
    }

    private ProductInventory mapToEntity(ProductRequestDTO dto) {
        return ProductInventory.builder()
                .productId(dto.getProductId())
                .productName(dto.getProductName())
                .buyPrice(dto.getBuyPrice())
                .sellingPrice(dto.getSellingPrice())
                .buyDate(dto.getBuyDate())
                .expiryDate(dto.getExpiryDate())
                .category(dto.getCategory())
                .type(dto.getType())
                .build();
    }

    private ProductRequestDTO mapToDTO(ProductInventory entity) {
        return ProductRequestDTO.builder()
                .productId(entity.getProductId())
                .productName(entity.getProductName())
                .buyPrice(entity.getBuyPrice())
                .sellingPrice(entity.getSellingPrice())
                .buyDate(entity.getBuyDate())
                .expiryDate(entity.getExpiryDate())
                .category(entity.getCategory())
                .type(entity.getType())
                .build();
    }

}
