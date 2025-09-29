package com.example.dto.vehicle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleDto {
    private Long id;
    private String plateNumber;
    private String type;
    private String status;
    private Long companyId;
}
