package com.example.service.service;

import com.example.dto.ApiResponse;
import com.example.dto.order.OrderCreateDto;
import com.example.dto.order.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderCreateDto create(OrderCreateDto dto);


    OrderCreateDto getById(Long id);
    Page<OrderDto> getAll(Pageable pageable);
    OrderCreateDto update(Long id,OrderCreateDto dto);
    ApiResponse<Boolean> delete(Long id);
    OrderDto assignDriver(Long orderId, Long vehicleId);
    OrderDto updateStatus(Long orderId, String status);
}
