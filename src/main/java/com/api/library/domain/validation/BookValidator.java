package com.api.library.domain.validation;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.Book;
import org.springframework.http.HttpStatus;

import static java.util.Objects.isNull;

public abstract class BookValidator {

    public void validateCreate(Book book) {
        validateCategory(book);
        validateIsbn(book);
    }

    private void validateCategory(Book book) {
        book.setCategory(book.getCategory().toUpperCase());
    }

    private void validateIsbn(Book book) {
        if (isNull(book.getIsbn()) || book.getIsbn().length() != 13) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "Invalid ISBN!");
        }
    }
}
