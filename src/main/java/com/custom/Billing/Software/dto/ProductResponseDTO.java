package com.custom.Billing.Software.dto;

import com.custom.Billing.Software.constant.EventType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductResponseDTO {

    private Long productId;
    private String productName;
    private Long buyPrice;
    private Long sellingPrice;
    private EventType category;
    private LocalDateTime buyDate;
    private LocalDateTime expiryDate;
}
