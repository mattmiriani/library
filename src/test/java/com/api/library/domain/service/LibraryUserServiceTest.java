package com.api.library.domain.service;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.LibraryUser;
import com.api.library.domain.repository.LibraryUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static java.util.Optional.*;

@ActiveProfiles("test")
class LibraryUserServiceTest {

    @Mock
    private LibraryUserRepository libraryUserRepository;

    @InjectMocks
    private LibraryUserService libraryUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private LibraryUser createLibraryUser(UUID libraryUserId) {
        var libraryUser = new LibraryUser();

        libraryUser.setId(libraryUserId);
        libraryUser.setName("name");
        libraryUser.setEmail("email@email.com");
        libraryUser.setCreatedAt(LocalDateTime.now());
        libraryUser.setNumberPhone("numberPhone");
        libraryUser.setActive(true);

        return libraryUser;
    }

    @Test
    void findAll() {
        var libraryUserId = UUID.randomUUID();
        var libraryUsersMock = Arrays.asList(
                createLibraryUser(UUID.randomUUID()),
                createLibraryUser(libraryUserId)
        );

        when(libraryUserRepository.findAll()).thenReturn(libraryUsersMock);

        var result = libraryUserService.findAll();

        assertEquals(2, result.size());
        assertEquals("name", result.get(0).getName());
        assertEquals(libraryUserId, result.get(1).getId());
    }

    @Test
    void findByIdSuccess() {
        var libraryUserId = UUID.randomUUID();
        var libraryUserMock = createLibraryUser(libraryUserId);

        when(libraryUserRepository.findById(libraryUserId)).thenReturn(of(libraryUserMock));

        var result = libraryUserService.findById(libraryUserId);

        assertNotNull(result);
        assertEquals(libraryUserId, result.getId());
        assertEquals("name", result.getName());
    }

    @Test
    void findByIdException() {
        var libraryUserId = UUID.randomUUID();

        when(libraryUserRepository.findById(libraryUserId)).thenReturn(empty());

        var exception = assertThrows(LibraryException.class, () -> libraryUserService.findById(libraryUserId));

        assertEquals("Library user not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void create() {
        var libraryUserId = UUID.randomUUID();
        var libraryUserMock = createLibraryUser(libraryUserId);

        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(libraryUserMock);

        var result = libraryUserService.create(libraryUserMock);

        assertNotNull(result);
        assertEquals(libraryUserId, result.getId());
        assertEquals("name", result.getName());
    }

    @Test
    void update() {
        var libraryUserId = UUID.randomUUID();
        var existingLibraryUser = createLibraryUser(libraryUserId);
        var updatedLibraryUser = createLibraryUser(libraryUserId);
        updatedLibraryUser.setName("Updated Name");

        when(libraryUserRepository.findById(libraryUserId)).thenReturn(of(existingLibraryUser));
        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(updatedLibraryUser);

        var result = libraryUserService.update(libraryUserId, updatedLibraryUser);

        assertNotNull(result);
        assertEquals(libraryUserId, result.getId());
        assertEquals("Updated Name", result.getName());
    }

    @Test
    void delete() {
        var libraryUserId = UUID.randomUUID();
        var libraryToDelete = createLibraryUser(libraryUserId);

        when(libraryUserRepository.findById(libraryUserId)).thenReturn(of(libraryToDelete));
        when(libraryUserRepository.save(any(LibraryUser.class))).thenReturn(libraryToDelete);

        libraryUserService.delete(libraryUserId);

        assertFalse(libraryToDelete.getActive());
        verify(libraryUserRepository).findById(libraryUserId);
        verify(libraryUserRepository).save(libraryToDelete);
    }
}