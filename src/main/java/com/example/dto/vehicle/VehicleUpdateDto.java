package com.example.dto.vehicle;

import com.example.enums.VehicleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleUpdateDto {
    private VehicleStatus status;
    private Long companyId;
}
