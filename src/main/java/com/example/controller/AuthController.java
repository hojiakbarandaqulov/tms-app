package com.example.controller;


import com.example.dto.ApiResponse;
import com.example.dto.auth.LoginDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.dto.auth.ResetPasswordDTO;
import com.example.dto.auth.UpdatePasswordDTO;
import com.example.service.AuthServiceImpl;
import com.example.service.impl.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @PostMapping(value = "registration")
    public ApiResponse<?> registration(@Valid @RequestBody RegistrationDTO dto) {
        return authService.registration(dto);
    }


    @PostMapping(value = "login")
    public ApiResponse<?> login(@Valid @RequestBody LoginDTO dto) {
        return authService.login(dto);
    }

    @GetMapping(value = "verification/{token}")
    public ApiResponse<String> verification(@PathVariable("token") String token) {
        return authService.verification(token);
    }

    @PostMapping("registration/reset")
    public ApiResponse<String> resent(@Valid @RequestBody ResetPasswordDTO dto) {
        return authService.resetPassword(dto);
    }

}

