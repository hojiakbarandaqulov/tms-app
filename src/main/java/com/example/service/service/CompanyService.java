package com.example.service.service;

import com.example.dto.company.CompanyRequestDto;
import com.example.dto.company.CompanyResponseDto;
import com.example.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    CompanyResponseDto create(CompanyRequestDto dto);
    CompanyResponseDto update(Long id, CompanyRequestDto dto);
    void delete(Long id);
    CompanyResponseDto getById(Long id);
    Page<CompanyResponseDto> getAll(Pageable pageable);
}