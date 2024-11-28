package com.api.library.application.controller;

import com.api.library.application.dto.LibraryUserCreateDTO;
import com.api.library.application.dto.LibraryUserDTO;
import com.api.library.domain.service.LibraryUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryUserController {

    private final LibraryUserService libraryUserService;

    @GetMapping
    public List<LibraryUserDTO> findAll() {
        return LibraryUserDTO.toLibraryUsersDTO(libraryUserService.findAll());
    }

    @GetMapping("/{libraryUserId}")
    public LibraryUserDTO findById(@PathVariable UUID libraryUserId) {
        return LibraryUserDTO.toLibraryUserDTO(libraryUserService.findById(libraryUserId));
    }

    @PostMapping("/email")
    public UUID findByEmail(@RequestBody LibraryUserDTO email) {
        return libraryUserService.findByEmail(email.email()).getId();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryUserCreateDTO create(@RequestBody LibraryUserCreateDTO libraryUserCreateDTO) {
        return LibraryUserCreateDTO.toLibraryUserCreateDTO(libraryUserService.create(libraryUserCreateDTO.toLibraryUser()));
    }

    @PutMapping("/{libraryUserId}")
    public LibraryUserDTO update(@PathVariable UUID libraryUserId,
                                 @RequestBody LibraryUserDTO libraryUserDTO) {
        return LibraryUserDTO.toLibraryUserDTO(libraryUserService.update(libraryUserId, libraryUserDTO.toLibraryUser()));
    }

    @DeleteMapping("/{libraryUserId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID libraryUserId) {
        libraryUserService.delete(libraryUserId);
    }
}
