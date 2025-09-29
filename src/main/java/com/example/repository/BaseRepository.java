package com.example.repository;

import com.example.model.BaseEntity;
import jakarta.transaction.Transactional;
import org.eclipse.angus.mail.imap.protocol.ID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {
    Optional<T> findByIdAndDeletedFalse(Long id);
//    Optional<T> deleted(Long id);


    List<T> findAllByDeletedFalse();
    Page<T> findAllByDeletedFalse(Pageable pageable);

//    List<Optional<T>> deletedList(List<Long> ids);


   /* @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true WHERE e.id = :id")
    void softDeleteById(@Param("id") ID id);

    @Modifying
    @Query("UPDATE #{#entityName} e SET e.deleted = true WHERE e.id IN :ids")
    int softDeleteAllByIds(@Param("ids") List<ID> ids);

    T saveAndRefresh(T entity);*/

}


