package com.example.repository;

import com.example.model.EmailHistory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailHistoryRepository extends BaseRepository<EmailHistory> {
    Long countByEmailAndCreatedDateBetween(String email, LocalDateTime from, LocalDateTime to);

    @Query(value = "select * from email_history where email=?1 order by created_date=desc limit 1", nativeQuery = true)
    Optional<EmailHistory> findTop1ByEmailOrderByCreatedDateDesc(String email);

    @Modifying
    @Transactional
    @Query("update EmailHistory set attemptCount = coalesce(attemptCount,0)+1 where  id=?1")
    void updateAttemptCount(Long id);

    Optional<EmailHistory> findByEmail(String toEmail);

    @Query(value = "select * from email_history where email=?1 and code=?2 order by created_date desc limit 1", nativeQuery = true)
    Optional<EmailHistory> findByEmailAndCode(String username, String confirmPassword);
}
