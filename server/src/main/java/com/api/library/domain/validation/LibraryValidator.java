package com.api.library.domain.validation;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.LibraryUser;
import com.api.library.domain.repository.LibraryUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public abstract class LibraryValidator {

    private final LibraryUserRepository libraryUserRepository;

    protected void validateCreate(LibraryUser libraryUser) {
        libraryUser.setCreatedAt(LocalDateTime.now());

        this.validateEmail(libraryUser.getEmail());
        this.validateCreationDate(libraryUser.getCreatedAt());
    }

    protected void validateUpdate(LibraryUser libraryUser) {
        this.validateEmail(libraryUser.getEmail());
    }

    private void validateEmail(String email) {
        if (isNull(email) || email.isEmpty()) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "Email is required!");
        }

        if (!EmailValidator.isValidEmail(email)) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "Invalid email!");
        }

        if (libraryUserRepository.findByEmail(email).isPresent()) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
    }

    private void validateCreationDate(LocalDateTime createdAt) {
        if (createdAt.isAfter(LocalDateTime.now())) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "Invalid creation date!");
        }
    }
}
