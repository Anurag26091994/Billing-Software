package com.custom.Billing.Software.repository;

import com.custom.Billing.Software.constant.EventType;
import com.custom.Billing.Software.entity.ProductInventory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory,Long> {

    Optional<ProductInventory> findByProductNameIgnoreCase(String productName);

    List<ProductInventory> findByProductNameContainingIgnoreCase(String name);

    // Optimized search
    @Query("SELECT p FROM ProductInventory p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductInventory> searchByproductName(@Param("keyword") String keyword);

}
