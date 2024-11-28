package com.api.library.domain.repository;

import com.api.library.domain.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LibraryUserRepository extends JpaRepository<LibraryUser, UUID> {
    Optional<LibraryUser> findByEmail(@Param("email") String email);
}
