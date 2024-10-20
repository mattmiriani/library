package com.api.library.domain.validation;

import com.api.library.config.LibraryException;
import com.api.library.domain.entity.Loan;
import org.springframework.http.HttpStatus;

public abstract class LoanValidator {

    protected void validateCreate(Loan loan) {
        validateCurrentLoan(loan);
    }

    private void validateCurrentLoan(Loan loan) {
        if (loan.getBook().getCheckLoan()) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "Book already loaned!");
        }
    }
}
