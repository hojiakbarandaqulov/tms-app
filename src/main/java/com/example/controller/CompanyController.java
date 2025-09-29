package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.company.CompanyRequestDto;
import com.example.dto.company.CompanyResponseDto;
import com.example.exp.AppBadException;
import com.example.model.Company;
import com.example.repository.CompanyRepository;
import com.example.service.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ApiResponse<CompanyResponseDto> create(@Valid @RequestBody CompanyRequestDto dto) {
        return ApiResponse.ok(companyService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("{id}")
    public ApiResponse<CompanyResponseDto> update(
            @PathVariable Long id,
             @Valid @RequestBody CompanyRequestDto dto
    ) {
        return ApiResponse.ok(companyService.update(id, dto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ApiResponse<Boolean> delete(@PathVariable Long id) {
        companyService.delete(id);
        return ApiResponse.ok(true);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("{id}")
    public ApiResponse<CompanyResponseDto> getById(@PathVariable Long id) {
        return ApiResponse.ok(companyService.getById(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ApiResponse<Page<CompanyResponseDto>> getAll(Pageable pageable) {
        Page<CompanyResponseDto> all = companyService.getAll(pageable);
        return ApiResponse.ok(all);
    }
}