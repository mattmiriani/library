package com.api.library.application.controller;

import com.api.library.application.dto.BookCreateDTO;
import com.api.library.application.dto.BookDTO;
import com.api.library.domain.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookDTO> findAll() {
        return BookDTO.toBooksDTO(bookService.findAll());
    }

    @GetMapping("/{bookId}")
    public BookDTO findById(@PathVariable UUID bookId) {
        return BookDTO.toBookDTO(bookService.findById(bookId));
    }

    @GetMapping("/recomendations/{libraryUserId}")
    public List<BookDTO> findRecomendations(@PathVariable UUID libraryUserId) {
        return BookDTO.toBooksDTO(bookService.findRecomendations(libraryUserId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookCreateDTO create(@RequestBody BookCreateDTO bookCreateDTO) {
        return BookCreateDTO.toBookCreateDTO(bookService.create(bookCreateDTO.toBook()));
    }

    @PutMapping("/{bookId}")
    public BookDTO update(@PathVariable UUID bookId, @RequestBody BookCreateDTO bookCreateDTO) {
        return BookDTO.toBookDTO(bookService.update(bookId, bookCreateDTO.toBook()));
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID bookId) {
        bookService.delete(bookId);
    }
}
