package com.example.service.impl;

import com.example.dto.driver.DriverCreateDto;
import com.example.dto.driver.DriverDto;
import com.example.exp.AppBadException;
import com.example.service.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Company;
import com.example.model.Driver;
import com.example.repository.CompanyRepository;
import com.example.repository.DriverRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public DriverCreateDto create(DriverCreateDto dto) {
        Driver driver = modelMapper.map(dto, Driver.class);
        driver.setId(null);
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppBadException("Company not found"));
        driver.setCompany(company);
        return modelMapper.map(driverRepository.save(driver), DriverCreateDto.class);
    }

    @Override
    public DriverDto getById(Long id) {
        return driverRepository.findById(id)
                .map(driver -> modelMapper.map(driver, DriverDto.class))
                .orElseThrow(() -> new AppBadException("Driver not found"));
    }

    @Override
    public Page<DriverDto> getAll(Pageable pageable) {
        return driverRepository.findAll(pageable)
                .map(driver -> modelMapper.map(driver, DriverDto.class));
    }

    @Override
    public DriverDto update( DriverDto dto) {
        Driver driver = driverRepository.findById(dto.getId())
                .orElseThrow(() -> new AppBadException("Driver not found"));
        driver.setFullName(dto.getFullName());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setPhone(dto.getPhone());
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppBadException("Company not found"));
        driver.setCompany(company);
        return modelMapper.map(driverRepository.save(driver), DriverDto.class);
    }

    @Override
    public void delete(Long id) {
        driverRepository.deleteById(id);
    }

    @Override
    public Page<DriverDto> getByCompany(Long companyId, Pageable pageable) {
        return driverRepository.findAllByCompanyId(companyId,pageable)
                .map(driver -> modelMapper.map(driver, DriverDto.class));
    }
}

