package com.api.library.application.dto;

import com.api.library.domain.entity.LibraryUser;

import java.util.List;

public record LibraryUserDTO(String name, String email, String numberPhone, Boolean active) {

    public LibraryUserDTO(LibraryUser libraryUser) {
        this(libraryUser.getName(), libraryUser.getEmail(), libraryUser.getNumberPhone(), libraryUser.getActive());
    }

    public static List<LibraryUserDTO> toLibraryUsersDTO(List<LibraryUser> libraryUser) {
        return libraryUser.stream().map(LibraryUserDTO::new).toList();
    }

    public static LibraryUserDTO toLibraryUserDTO(LibraryUser libraryUser) {
        return new LibraryUserDTO(libraryUser);
    }

    public LibraryUser toLibraryUser() {
        var libraryUser = new LibraryUser();

        libraryUser.setName(name);
        libraryUser.setEmail(email);
        libraryUser.setNumberPhone(numberPhone);

        return libraryUser;
    }
}
