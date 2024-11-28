package com.api.library.domain.service;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.LibraryUser;
import com.api.library.domain.repository.LibraryUserRepository;
import com.api.library.domain.validation.LibraryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class LibraryUserService extends LibraryValidator {

    private final LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserService(LibraryUserRepository libraryUserRepository) {
        super(libraryUserRepository);
        this.libraryUserRepository = libraryUserRepository;
    }

    @Transactional(readOnly = true)
    public List<LibraryUser> findAll() {
        return libraryUserRepository.findAll();
    }

    @Transactional(readOnly = true)
    public LibraryUser findById(UUID libraryUserId) {
        return libraryUserRepository.findById(libraryUserId).orElseThrow(
                () -> new LibraryException(HttpStatus.NOT_FOUND, "Library user not found")
        );
    }

    @Transactional(readOnly = true)
    public LibraryUser findByEmail(String email) {
        return libraryUserRepository.findByEmail(email).orElseThrow(
                () -> new LibraryException(HttpStatus.NOT_FOUND, "Library user not found")
        );
    }

    private LibraryUser save(LibraryUser libraryUser) {
        return libraryUserRepository.save(libraryUser);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LibraryUser create(LibraryUser libraryUser) {
        this.validateCreate(libraryUser);

        return this.save(libraryUser);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public LibraryUser update(UUID libraryUserId, LibraryUser libraryUser) {
        this.validateUpdate(libraryUser);

        var libraryUserToUpdate = this.findById(libraryUserId);
        libraryUserToUpdate.mergeForUpdate(libraryUser);

        return this.save(libraryUserToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(UUID libraryUserId) {
        var libraryUserToDelete = this.findById(libraryUserId);

        if (libraryUserToDelete.getActive()) {
            libraryUserToDelete.setActive(false);

            this.save(libraryUserToDelete);
        }
    }
}
