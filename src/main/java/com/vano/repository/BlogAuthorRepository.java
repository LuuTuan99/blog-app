package com.vano.repository;

import com.vano.entity.BlogAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BlogAuthorRepository extends JpaRepository<BlogAuthor, Long>, JpaSpecificationExecutor<BlogAuthor> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "SET FOREIGN_KEY_CHECKS=0;")
    void disableForeignKeyCheck();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "SET FOREIGN_KEY_CHECKS=1;")
    void enableForeignKeyCheck();

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "alter table member AUTO_INCREMENT = 1")
    void resetIncrement();

    Optional<BlogAuthor> findByEmail(String email);

    Optional<BlogAuthor> findByUsername(String username);
}
