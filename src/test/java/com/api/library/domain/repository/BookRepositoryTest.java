package com.api.library.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testFindRecommendations() {
        var libraryUserId = UUID.fromString("f54d2b5d-67fd-4cec-aae0-7d2e5f4e01ef");
        var recommendations = bookRepository.findRecomendations(libraryUserId);

        assertTrue(recommendations.isPresent());

        var books = recommendations.get();
        assertEquals(2, books.size());

        assertEquals("Book 2", books.get(0).getTitle());
        assertEquals("Category A", books.get(0).getCategory());
    }
}

