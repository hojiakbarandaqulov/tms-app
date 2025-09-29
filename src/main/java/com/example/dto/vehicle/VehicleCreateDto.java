package com.example.dto.vehicle;

import com.example.enums.VehicleStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehicleCreateDto {
    @NotBlank(message = "plateNumber required")
    private String plateNumber;
    @NotBlank(message = "type required")
    private String type;
    @NotNull(message = "companyId not null")
    private Long companyId;
}
