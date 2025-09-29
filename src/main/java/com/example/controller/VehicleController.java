package com.example.controller;


import com.example.dto.ApiResponse;
import com.example.dto.vehicle.VehicleCreateDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleUpdateDto;
import com.example.model.Vehicle;
import com.example.service.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<VehicleCreateDto> create(@Valid @RequestBody VehicleCreateDto vehicle) {
        VehicleCreateDto vehicleCreateDto = vehicleService.create(vehicle);
        return ApiResponse.ok(vehicleCreateDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ApiResponse<VehicleDto> getById(@PathVariable Long id) {
        return ApiResponse.ok(vehicleService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ApiResponse<Page<VehicleDto>> getAll(Pageable pageable) {
        return ApiResponse.ok(vehicleService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}/status")
    public ApiResponse<VehicleUpdateDto> updateStatus(@PathVariable Long id, @Valid @RequestBody VehicleUpdateDto vehicle) {
        VehicleUpdateDto update = vehicleService.update(id, vehicle);
        return ApiResponse.ok(update);
    }
}

