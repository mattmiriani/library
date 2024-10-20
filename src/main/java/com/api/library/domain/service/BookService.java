package com.api.library.domain.service;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.Book;
import com.api.library.domain.repository.BookRepository;
import com.api.library.domain.validation.BookValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService extends BookValidator {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(UUID bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new LibraryException(HttpStatus.NOT_FOUND, "Book not found")
        );
    }

    @Transactional(readOnly = true)
    public List<Book> findRecomendations(UUID libraryUserId) {
        return bookRepository.findRecomendations(libraryUserId).orElseThrow(
                () -> new LibraryException(HttpStatus.NOT_FOUND, "Recomendations not found")
        );
    }

    protected Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Book create(Book book) {
        this.validateCreate(book);

        return this.save(book);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Book update(UUID bookId, Book book) {
        var bookToUpdate = this.findById(bookId);
        bookToUpdate.mergeForUpdate(book);

        return this.save(bookToUpdate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(UUID bookId) {
        var bookToDelete = this.findById(bookId);

        if (bookToDelete.getActive()) {
            bookToDelete.setActive(false);

            this.save(bookToDelete);
        }
    }
}
