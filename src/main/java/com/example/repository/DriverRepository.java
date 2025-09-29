package com.example.repository;

import com.example.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverRepository extends BaseRepository<Driver> {
    Page<Driver> findAllByCompanyId(Long companyId, Pageable pageable);

}
