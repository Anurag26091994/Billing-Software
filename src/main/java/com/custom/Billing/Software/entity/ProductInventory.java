package com.custom.Billing.Software.entity;

import com.custom.Billing.Software.constant.EventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "product_inventory",
        indexes = {
                @Index(name = "idx_product_name", columnList = "productName"),
                @Index(name = "idx_category", columnList = "category")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "productName")
        })
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ProductInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long buyPrice;
    private Long sellingPrice;

    @CreatedDate
    @JsonIgnoreProperties
    @Column(nullable = false, updatable = false)
    private LocalDateTime buyDate;


    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType category;

    @Version
    private Long version;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String,String> type; // Map<"write here for which decease it used","Alternative option for same medicine">

    public ProductInventory() {
    }

    @Override
    public String toString() {
        return "ProductInventory{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", buyPrice=" + buyPrice +
                ", sellingPrice=" + sellingPrice +
                ", buyDate=" + buyDate +
                ", expiryDate=" + expiryDate +
                ", category=" + category +
                ", version=" + version +
                ", type=" + type +
                '}';
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Long getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Long sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public LocalDateTime getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDateTime buyDate) {
        this.buyDate = buyDate;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public EventType getCategory() {
        return category;
    }

    public void setCategory(EventType category) {
        this.category = category;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Map<String, String> getType() {
        return type;
    }

    public void setType(Map<String, String> type) {
        this.type = type;
    }

    public ProductInventory(Long productId, String productName, Long buyPrice, Long sellingPrice, LocalDateTime buyDate, LocalDateTime expiryDate, EventType category, Long version, Map<String, String> type) {
        this.productId = productId;
        this.productName = productName;
        this.buyPrice = buyPrice;
        this.sellingPrice = sellingPrice;
        this.buyDate = buyDate;
        this.expiryDate = expiryDate;
        this.category = category;
        this.version = version;
        this.type = type;
    }
}
