package com.example.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatePasswordDTO {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "confirmPassword required")
    private String confirmPassword;

    @NotBlank(message = "password required")
    private String newPassword;

}
