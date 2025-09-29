package com.example.dto.order;

import com.example.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderCreateDto {
    @NotNull(message ="shipperId required" )
    private Long shipperId;
    @NotNull(message = "carrierId required")
    private Long carrierId;
    @NotNull(message = "vehicleId required")
    private Long vehicleId;
    @NotBlank(message = "origin required")
    private String origin;
    @NotBlank(message = "destination required")
    private String destination;
    private OrderStatus status;
}