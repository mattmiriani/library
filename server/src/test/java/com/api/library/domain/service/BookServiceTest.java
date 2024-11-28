package com.api.library.domain.service;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.Book;
import com.api.library.domain.repository.BookRepository;
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
import static org.mockito.Mockito.*;
import static java.util.Optional.*;

@ActiveProfiles("test")
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Book createBook(UUID bookId) {
        var book = new Book();

        book.setId(bookId);
        book.setTitle("title");
        book.setAuthor("author");
        book.setIsbn("1234567891011");
        book.setPublishedAt(LocalDateTime.now());
        book.setCategory("category");
        book.setActive(true);

        return book;
    }

    @Test
    void findAll() {
        var bookId = UUID.randomUUID();
        var booksMock = Arrays.asList(
                createBook(UUID.randomUUID()),
                createBook(bookId)
        );

        when(bookRepository.findAll()).thenReturn(booksMock);

        var result = bookService.findAll();

        assertEquals(2, result.size());
        assertEquals("title", result.get(0).getTitle());
        assertEquals(bookId, result.get(1).getId());
    }

    @Test
    void findByIdSuccess() {
        var bookId = UUID.randomUUID();
        var bookMock = createBook(bookId);

        when(bookRepository.findById(bookId)).thenReturn(of(bookMock));

        var result = bookService.findById(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals("title", result.getTitle());
    }

    @Test
    void findByIdException() {
        var bookId = UUID.randomUUID();

        when(bookRepository.findById(bookId)).thenReturn(empty());

        var exception = assertThrows(LibraryException.class, () -> bookService.findById(bookId));

        assertEquals("Book not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void findRecomendationsSuccess() {
        var libraryUserId = UUID.randomUUID();
        var bookId = UUID.randomUUID();
        var booksMock = Arrays.asList(
                createBook(UUID.randomUUID()),
                createBook(bookId)
        );

        when(bookRepository.findRecomendations(libraryUserId)).thenReturn(of(booksMock));

        var result = bookService.findRecomendations(libraryUserId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("title", result.get(0).getTitle());
        assertEquals(bookId, result.get(1).getId());
    }

    @Test
    void findRecomendationsException() {
        var libraryUserId = UUID.randomUUID();

        when(bookRepository.findRecomendations(libraryUserId)).thenReturn(empty());

        LibraryException exception = assertThrows(LibraryException.class, () -> bookService.findRecomendations(libraryUserId));

        assertEquals("Recomendations not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void create() {
        var bookToCreate = createBook(UUID.randomUUID());

        when(bookService.save(bookToCreate)).thenReturn(bookToCreate);

        var result = bookService.create(bookToCreate);

        assertNotNull(result);
        assertEquals(bookToCreate.getId(), result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("author", result.getAuthor());

        verify(bookRepository, times(1)).save(bookToCreate);
    }

    @Test
    void update() {
        var bookId = UUID.randomUUID();
        var existingBook = createBook(bookId);
        var updatedBook = createBook(bookId);
        updatedBook.setTitle("Updated Title");

        when(bookRepository.findById(bookId)).thenReturn(of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(existingBook);

        var result = bookService.update(bookId, updatedBook);

        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(existingBook);

        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void delete() {
        var bookId = UUID.randomUUID();
        var bookToDelete = createBook(bookId);

        when(bookRepository.findById(bookId)).thenReturn(of(bookToDelete));
        when(bookRepository.save(any(Book.class))).thenReturn(bookToDelete);

        bookService.delete(bookId);

        assertFalse(bookToDelete.getActive());
        verify(bookRepository, times(1)).findById(bookId);
        verify(bookRepository, times(1)).save(bookToDelete);
    }
}