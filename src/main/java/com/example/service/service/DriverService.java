package com.example.service.service;

import com.example.dto.driver.DriverCreateDto;
import com.example.dto.driver.DriverDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

public interface DriverService {
    DriverCreateDto create(DriverCreateDto dto);
    DriverDto getById(Long id);

    Page<DriverDto> getAll(Pageable pageable);

    DriverDto update(DriverDto dto);
    void delete(Long id);
    Page<DriverDto> getByCompany(Long companyId, Pageable pageable);
}
