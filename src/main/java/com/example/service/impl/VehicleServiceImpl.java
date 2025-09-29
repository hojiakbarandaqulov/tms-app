package com.example.service.impl;


import com.example.dto.vehicle.VehicleCreateDto;
import com.example.dto.vehicle.VehicleDto;
import com.example.dto.vehicle.VehicleUpdateDto;
import com.example.enums.VehicleStatus;
import com.example.exp.AppBadException;
import com.example.model.Vehicle;
import com.example.repository.VehicleRepository;
import com.example.service.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final CompanyServiceImpl companyService;
    private final ModelMapper mapper;

    @Override
    public VehicleCreateDto create(VehicleCreateDto dto) {
        Vehicle vehicle = mapper.map(dto, Vehicle.class);
        vehicle.setId(null);
        vehicle.setCompany(companyService.get(dto.getCompanyId()));
        vehicle.setStatus(VehicleStatus.ACTIVE);
        Vehicle vehicleSave = vehicleRepository.save(vehicle);
        return mapper.map(vehicleSave,VehicleCreateDto.class);
    }

    @Override
    public VehicleDto getById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Vehicle not found"));
        return mapper.map(vehicle, VehicleDto.class);
    }

    @Override
    public Page<VehicleDto> getAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable)
                .map(vehicle -> mapper.map(vehicle, VehicleDto.class));
    }

    @Override
    public VehicleUpdateDto update(Long id, VehicleUpdateDto dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new AppBadException("Vehicle not found"));
      /*  vehicle.setPlateNumber(dto.getPlateNumber());
        vehicle.setType(dto.getType());*/
        vehicle.setStatus(dto.getStatus());
        vehicle.setCompany(companyService.get(dto.getCompanyId()));
        return mapper.map(vehicleRepository.save(vehicle), VehicleUpdateDto.class);
    }

    @Override
    public void delete(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new AppBadException("Vehicle not found");
        }
        vehicleRepository.deleteById(id);
    }
}


