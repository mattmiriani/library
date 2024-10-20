package com.api.library.domain.service;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.Loan;
import com.api.library.domain.repository.LoanRepository;
import com.api.library.domain.validation.LoanValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanService extends LoanValidator {

    private final LoanRepository loanRepository;
    private final LibraryUserService libraryUserService;
    private final BookService bookService;

    @Transactional(readOnly = true)
    public List<Loan> findAll(UUID libraryUserId) {
        var loanList = loanRepository.findAll();

        return loanList.stream().filter(loan -> loan.getLibraryUser().getId().equals(libraryUserId)).toList();
    }

    @Transactional(readOnly = true)
    public Loan findById(UUID loanId) {
        return loanRepository.findById(loanId).orElseThrow(
                () -> new LibraryException(HttpStatus.NOT_FOUND, "Loan not found")
        );
    }

    private Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Loan create(Loan loan) {
        var libraryUserValid = libraryUserService.findById(loan.getLibraryUser().getId());
        var bookValid = bookService.findById(loan.getBook().getId());

        loan.setLibraryUser(libraryUserValid);
        loan.setBook(bookValid);
        loan.setBorrowedAt(LocalDateTime.now());
        loan.setActive(TRUE);

        validateCreate(loan);

        return save(loan);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Loan update(UUID loanId) {
        var loanToUpdate = this.findById(loanId);

        loanToUpdate.setReturnedAt(LocalDateTime.now());
        loanToUpdate.setActive(FALSE);

        return save(loanToUpdate);
    }
}
