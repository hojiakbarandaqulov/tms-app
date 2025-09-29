package com.example.controller;


import com.example.dto.ApiResponse;
import com.example.dto.driver.DriverCreateDto;
import com.example.dto.driver.DriverDto;
import com.example.service.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<DriverCreateDto> create(@Valid @RequestBody DriverCreateDto dto) {
        return ApiResponse.ok(driverService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ApiResponse<DriverDto> getById(@PathVariable Long id) {
        return ApiResponse.ok(driverService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ApiResponse<Page<DriverDto>> getAll(Pageable pageable) {
        return ApiResponse.ok(driverService.getAll(pageable));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update")
    public ApiResponse<DriverDto> update(@Valid @RequestBody DriverDto dto) {
        return ApiResponse.ok(driverService.update(dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        driverService.delete(id);
        return ApiResponse.ok(true);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("company/{companyId}")
    public ApiResponse<Page<DriverDto>> getByCompany(@PathVariable Long companyId, Pageable pageable) {
        return ApiResponse.ok(driverService.getByCompany(companyId, pageable));
    }
}
