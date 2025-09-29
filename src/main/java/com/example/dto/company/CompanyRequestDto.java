package com.example.dto.company;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyRequestDto {
    @NotBlank(message = "name required")
    private String name;
    @NotBlank(message = "address required")
    private String address;
}