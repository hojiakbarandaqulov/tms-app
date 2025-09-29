package com.example.controller;


import com.example.dto.ApiResponse;
import com.example.dto.order.OrderCreateDto;
import com.example.dto.order.OrderDto;
import com.example.service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<OrderCreateDto> create(@Valid  @RequestBody OrderCreateDto dto) {
        return ApiResponse.ok(orderService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ApiResponse<OrderCreateDto> getById(@PathVariable Long id) {
        return ApiResponse.ok(orderService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ApiResponse<Page<OrderDto>> getAll(Pageable pageable) {
        return ApiResponse.ok(orderService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ApiResponse<OrderCreateDto> update(@PathVariable Long id, @RequestBody OrderCreateDto dto) {
        return ApiResponse.ok(orderService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        return orderService.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}/assign-driver")
    public ApiResponse<OrderDto> assignDriver(@PathVariable Long id, @RequestParam Long vehicleId) {
        return ApiResponse.ok(orderService.assignDriver(id, vehicleId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}/status")
    public ApiResponse<OrderDto> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return ApiResponse.ok(orderService.updateStatus(id, status));
    }
}