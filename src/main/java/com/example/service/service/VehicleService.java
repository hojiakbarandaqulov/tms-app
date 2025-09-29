package com.example.service.service;

import com.example.dto.vehicle.VehicleCreateDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleUpdateDto;
import com.example.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    VehicleCreateDto create(VehicleCreateDto dto);
    VehicleDto getById(Long id);
    Page<VehicleDto> getAll(Pageable pageable);

    VehicleUpdateDto update(Long id, VehicleUpdateDto dto);

    void delete(Long id);
}



