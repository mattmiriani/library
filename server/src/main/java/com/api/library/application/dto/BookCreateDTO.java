package com.api.library.application.dto;

import com.api.library.domain.entity.Book;

import java.time.LocalDateTime;
import java.util.UUID;

public record BookCreateDTO(UUID id, String title, String author, String isbn, LocalDateTime publishedAt, String category, Boolean active) {

    public BookCreateDTO(Book book) {
        this(book.getId(), book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedAt(), book.getCategory(), book.getActive());
    }

    public static BookCreateDTO toBookCreateDTO(Book book) {
        return new BookCreateDTO(book);
    }

    public Book toBook() {
        var book = new Book();

        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublishedAt(publishedAt);
        book.setCategory(category);
        book.setActive(active);

        return book;
    }
}
