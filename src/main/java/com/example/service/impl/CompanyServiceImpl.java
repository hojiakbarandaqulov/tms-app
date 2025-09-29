package com.example.service.impl;

import com.example.dto.company.CompanyRequestDto;
import com.example.dto.company.CompanyResponseDto;
import com.example.exp.AppBadException;
import com.example.model.Company;
import com.example.repository.CompanyRepository;
import com.example.service.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public CompanyResponseDto create(CompanyRequestDto dto) {
        Company company = modelMapper.map(dto, Company.class);
        Company saved = companyRepository.save(company);
        return modelMapper.map(saved, CompanyResponseDto.class);
    }

    @Override
    public CompanyResponseDto update(Long id, CompanyRequestDto dto) {
        Company company = companyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setName(dto.getName());
        company.setAddress(dto.getAddress());

        Company updated = companyRepository.save(company);
        return modelMapper.map(updated, CompanyResponseDto.class);
    }

    @Override
    public void delete(Long id) {
        Company company = companyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        company.setDeleted(true);
        companyRepository.save(company);
    }

    @Override
    public CompanyResponseDto getById(Long id) {
        Company company = companyRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return modelMapper.map(company, CompanyResponseDto.class);
    }

    @Override
    public Page<CompanyResponseDto> getAll(Pageable pageable) {
        Page<Company> allByDeletedFalse = companyRepository.findAllByDeletedFalse(pageable);
        return allByDeletedFalse.map(company -> modelMapper.map(company, CompanyResponseDto.class));
    }

    public Company get(Long id) {
        return companyRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new AppBadException("Company not found"));
    }

}

