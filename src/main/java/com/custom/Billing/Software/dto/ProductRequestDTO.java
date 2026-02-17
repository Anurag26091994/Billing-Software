package com.custom.Billing.Software.dto;

import com.custom.Billing.Software.constant.EventType;
import lombok.Builder;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor   // ✅ REQUIRED
@AllArgsConstructor
@Builder
public class ProductRequestDTO {

    private Long productId;
    private String productName;
    private Long buyPrice;
    private Long sellingPrice;
    private EventType category;
    private LocalDateTime buyDate;
    private LocalDateTime expiryDate;
    private Map<String, String> type;
}

