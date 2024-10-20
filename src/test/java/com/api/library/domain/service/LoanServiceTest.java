package com.api.library.domain.service;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.Book;
import com.api.library.domain.entity.LibraryUser;
import com.api.library.domain.entity.Loan;
import com.api.library.domain.repository.LoanRepository;
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

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static java.util.Optional.*;

@ActiveProfiles("test")
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LibraryUserService libraryUserService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Loan createLoan(UUID loanId, UUID libraryUserId, UUID bookId) {
        var loan = new Loan();
        var libraryUser = new LibraryUser();
        var book = new Book();

        libraryUser.setId(libraryUserId);
        book.setId(bookId);

        loan.setId(loanId);
        loan.setLibraryUser(libraryUser);
        loan.setBook(book);
        loan.setActive(true);

        return loan;
    }

    @Test
    void findAll() {
        var libraryUserId = UUID.randomUUID();
        var loanList = Arrays.asList(
                createLoan(UUID.randomUUID(), libraryUserId, UUID.randomUUID()),
                createLoan(UUID.randomUUID(), libraryUserId, UUID.randomUUID())
        );

        when(loanRepository.findAll()).thenReturn(loanList);

        var result = loanService.findAll(libraryUserId);

        assertEquals(2, result.size());
        assertEquals(libraryUserId, result.get(0).getLibraryUser().getId());
    }

    @Test
    void findByIdSuccess() {
        var loanId = UUID.randomUUID();
        var libraryUserId = UUID.randomUUID();
        var bookId = UUID.randomUUID();
        var loan = createLoan(loanId, libraryUserId, bookId);

        when(loanRepository.findById(loanId)).thenReturn(of(loan));

        var result = loanService.findById(loanId);

        assertEquals(loanId, result.getId());
        assertEquals(libraryUserId, result.getLibraryUser().getId());
        assertEquals(bookId, result.getBook().getId());
    }

    @Test
    void findByIdException() {
        var loanId = UUID.randomUUID();

        when(loanRepository.findById(loanId)).thenReturn(empty());

        var exception = assertThrows(LibraryException.class, () -> loanService.findById(loanId));

        assertEquals("Loan not found", exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void create() {
        var loanId = UUID.randomUUID();
        var libraryUserId = UUID.randomUUID();
        var bookId = UUID.randomUUID();
        var loan = createLoan(loanId, libraryUserId, bookId);

        var validLibraryUser = new LibraryUser();
        validLibraryUser.setId(libraryUserId);

        var validBook = new Book();
        validBook.setId(bookId);

        when(libraryUserService.findById(libraryUserId)).thenReturn(validLibraryUser);
        when(bookService.findById(bookId)).thenReturn(validBook);
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = loanService.create(loan);

        assertEquals(validLibraryUser, result.getLibraryUser());
        assertEquals(validBook, result.getBook());
        assertEquals(LocalDateTime.now().getHour(), result.getBorrowedAt().getHour());
        assertEquals(TRUE, result.getActive());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void update() {
        var loanId = UUID.randomUUID();
        var libraryUserId = UUID.randomUUID();
        var bookId = UUID.randomUUID();
        var loan = createLoan(loanId, libraryUserId, bookId);

        when(loanRepository.findById(loanId)).thenReturn(java.util.Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var result = loanService.update(loanId);

        assertEquals(LocalDateTime.now().getHour(), result.getReturnedAt().getHour());
        assertEquals(FALSE, result.getActive());
        verify(loanRepository, times(1)).save(loan);
    }
}