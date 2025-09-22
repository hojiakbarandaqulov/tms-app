package com.example.service;

import com.example.exp.AppBadException;
import com.example.model.Profile;
import com.example.repository.ProfileRepository;
import com.example.service.impl.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile getById(Long id) {
        return profileRepository.findById(id).orElseThrow(() -> new AppBadException("Profile not found"));
    }
}
