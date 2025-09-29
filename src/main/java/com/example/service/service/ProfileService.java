package com.example.service.service;

import com.example.model.Profile;
import org.springframework.stereotype.Service;

@Service
public interface ProfileService {
    Profile getById(Long id);
}
