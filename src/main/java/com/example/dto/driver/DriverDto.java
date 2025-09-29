package com.example.dto.driver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDto {
    private Long id;
    @NotBlank(message = "fullName required")
    private String fullName;
    @NotBlank(message = "licenseNumber required")
    private String licenseNumber;
    @NotBlank(message = "phone required")
    private String phone;
    @NotNull(message = "companyId required")
    private Long companyId;
}
