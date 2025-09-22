package com.example.service.impl;

import com.example.dto.ApiResponse;
import com.example.dto.auth.LoginDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.dto.auth.ResetPasswordDTO;
import com.example.dto.auth.UpdatePasswordDTO;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    ApiResponse<?> registration(RegistrationDTO dto);

    ApiResponse<?> login(LoginDTO dto);

    ApiResponse<String> verification(String token);

    ApiResponse<String> resetPassword(ResetPasswordDTO dto);

    ApiResponse<?> updatePassword(UpdatePasswordDTO dto);
}
