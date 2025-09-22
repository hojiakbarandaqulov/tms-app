package com.example.repository;

import com.example.enums.GeneralStatus;
import com.example.model.Profile;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends BaseRepository<Profile>{

  Optional<Profile> findByEmailAndDeletedTrue(String email);

  @Modifying
  @Transactional
  @Query("update Profile  set status=?2 where id=?1")
  void changeStatus(Long profileId, GeneralStatus generalStatus);

  @Modifying
  @Transactional
  @Query("update Profile set password=?2 where id =?1")
  void updatePassword(Long id, String encode);
}
