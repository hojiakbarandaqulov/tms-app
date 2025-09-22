package com.example.service;
import com.example.enums.EmailType;
import com.example.exp.AppBadException;
import com.example.model.EmailHistory;
import com.example.repository.EmailHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmailHistoryService {
    private final EmailHistoryRepository emailHistoryRepository;


    public void create(String email, String code, EmailType emailType){
        EmailHistory emailHistoryEntity = new EmailHistory();
        emailHistoryEntity.setEmail(email);
        emailHistoryEntity.setCode(code);
        emailHistoryEntity.setEmailType(emailType);
        emailHistoryEntity.setAttemptCount(0);
        emailHistoryEntity.setCreatedDate(LocalDateTime.now());
        emailHistoryRepository.save(emailHistoryEntity);
    }

    public Long getEmailCount(String email){
        LocalDateTime now = LocalDateTime.now();
        return emailHistoryRepository.countByEmailAndCreatedDateBetween(email,now.minusMinutes(1),now);
    }

    public void check(String email, String code){
        Optional<EmailHistory> optional = emailHistoryRepository.findTop1ByEmailOrderByCreatedDateDesc(email);

        if(optional.isEmpty()){
            throw  new AppBadException("verification.failed");
        }
        EmailHistory entity = optional.get();

        if (entity.getAttemptCount()>=3){
            throw  new AppBadException("verification.failed");
        }

        if (!entity.getCode().equals(code)){
            emailHistoryRepository.updateAttemptCount(entity.getId());
            throw  new AppBadException("verification.failed");
        }

        LocalDateTime expDate=entity.getCreatedDate().plusMinutes(2);
        if (LocalDateTime.now().isAfter(expDate)){
            throw  new AppBadException("verification failed");
        }
    }
}
