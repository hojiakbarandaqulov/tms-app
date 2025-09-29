package com.example.controller;

import com.example.dto.ApiResponse;
import com.example.dto.auth.UpdatePasswordDTO;
import com.example.service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("profile")
public class ProfileController {
    private final AuthService authService;

    @PutMapping("password/update")
    public ApiResponse<?> updatePassword(@Valid @RequestBody UpdatePasswordDTO dto){
        return authService.updatePassword(dto);
    }
}
