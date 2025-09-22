package com.example.service;

import com.example.config.SpringConfig;
import com.example.dto.ApiResponse;
import com.example.dto.auth.*;
import com.example.enums.GeneralStatus;
import com.example.enums.RoleEnum;
import com.example.exp.AppBadException;
import com.example.model.Profile;
import com.example.repository.ProfileRepository;
import com.example.service.impl.AuthService;
import com.example.service.impl.ProfileService;
import com.example.util.EmailUtil;
import com.example.util.JwtUtil;
import com.example.util.SpringSecurityUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final ProfileRepository profileRepository;
    private final EmailSendingService emailSendingService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ProfileService profileService;

    @Override
    public ApiResponse<String> registration(RegistrationDTO dto) {
        Optional<Profile> profile = profileRepository.findByEmailAndDeletedTrue(dto.getEmail());
        if (profile.isPresent()) {
            Profile profileEntity = profile.get();
            if (profileEntity.getStatus().equals(GeneralStatus.REGISTRATION)) {
                profileRepository.delete(profileEntity);
            } else {
                throw new AppBadException("profile already exists");
            }
        }
        Profile entity = new Profile();
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.REGISTRATION);
        entity.setRole(RoleEnum.ROLE_USER);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        emailSendingService.sendRegistrationEmail(dto.getEmail(), entity.getId());
        return ApiResponse.ok("registration successful");
    }

    @Override
    public ApiResponse<?> login(LoginDTO dto) {
        Optional<Profile> loginProfile = profileRepository.findByEmailAndDeletedTrue(dto.getEmail());
        if (loginProfile.isEmpty()) {
            throw new AppBadException("profile password wrong");
        }
        Profile entity = loginProfile.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), entity.getPassword())) {
            throw new AppBadException("password wrong");
        }
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException("profile status wrong");
        }
        ProfileDTO response = new ProfileDTO();
        response.setFullName(entity.getFullName());
        response.setEmail(entity.getEmail());
        response.setRoleEnum(entity.getRole());
        response.setJwt(JwtUtil.encode(response.getEmail(), entity.getId(), response.getRoleEnum()));
        return ApiResponse.ok(response);
    }


    public ApiResponse<String> verification(String token) {
        try {
            Long profileId = JwtUtil.decodeVerRegToken(token);
            Profile profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.REGISTRATION)) {
                profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
                return  ApiResponse.ok("verification successful");
            }
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }
        throw new AppBadException("verification.wrong");
    }

    @Override
    public ApiResponse<String> resetPassword(ResetPasswordDTO dto) {
        Optional<Profile> optional = profileRepository.findByEmailAndDeletedTrue(dto.getEmail());
        if (optional.isEmpty()) {
            log.warn("User not found => {}", dto.getEmail());
            throw new AppBadException("profile password wrong");
        }
        Profile entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)) {
            log.warn("User status wrong => {}", dto.getEmail());
            throw new AppBadException("profile status wrong");
        }

        emailSendingService.sentResetPasswordEmail(entity.getEmail());
        return ApiResponse.ok("resent code sent");
    }

    @Override
    public ApiResponse<?> updatePassword(UpdatePasswordDTO dto) {
        Optional<Profile> optional = profileRepository.findByEmailAndDeletedTrue(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException("verification wrong");
        }
        Profile profile = optional.get();
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException("wrong status");
        }if (EmailUtil.isEmail(dto.getUsername()) ){
            emailSendingService.sentResetPasswordEmail(dto.getUsername());
        }
        profileRepository.updatePassword(profile.getId(), bCryptPasswordEncoder.encode(dto.getNewPassword()));
        return ApiResponse.ok("reset password response");
    }

}
