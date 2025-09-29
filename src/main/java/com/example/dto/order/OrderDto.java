package com.example.dto.order;

import com.example.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long shipperId;
    private Long carrierId;
    private Long vehicleId;
    private String origin;
    private String destination;
    private OrderStatus status;
}