package com.api.library.application.dto;

import com.api.library.domain.entity.LibraryUser;

import java.util.UUID;

public record LibraryUserCreateDTO(UUID id, String name, String email, String numberPhone) {

    public LibraryUserCreateDTO(LibraryUser libraryUser) {
        this(libraryUser.getId(), libraryUser.getName(), libraryUser.getEmail(), libraryUser.getNumberPhone());
    }

    public static LibraryUserCreateDTO toLibraryUserCreateDTO(LibraryUser libraryUser) {
        return new LibraryUserCreateDTO(libraryUser);
    }

    public LibraryUser toLibraryUser() {
        var libraryUser = new LibraryUser();

        libraryUser.setId(id);
        libraryUser.setName(name);
        libraryUser.setEmail(email);
        libraryUser.setNumberPhone(numberPhone);

        return libraryUser;
    }
}
