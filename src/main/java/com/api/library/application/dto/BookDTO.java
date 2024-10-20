package com.api.library.application.dto;

import com.api.library.domain.entity.Book;

import java.time.LocalDateTime;
import java.util.List;

public record BookDTO(String title, String author, String isbn, LocalDateTime publishedAt, String category, Boolean active) {

    public BookDTO(Book book) {
        this(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedAt(), book.getCategory(), book.getActive());
    }

    public static List<BookDTO> toBooksDTO(List<Book> book) {
        return book.stream().map(BookDTO::new).toList();
    }

    public static BookDTO toBookDTO(Book book) {
        return new BookDTO(book);
    }

    public Book toBook() {
        var book = new Book();

        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setPublishedAt(publishedAt);
        book.setCategory(category);
        book.setActive(active);

        return book;
    }

}
