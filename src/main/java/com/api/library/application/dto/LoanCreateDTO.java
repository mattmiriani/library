package com.api.library.application.dto;

import com.api.library.domain.entity.Loan;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoanCreateDTO(UUID id, LocalDateTime borrowedAt, LocalDateTime returnedAt, Boolean active, BookCreateDTO book, LibraryUserCreateDTO libraryUser) {

    public LoanCreateDTO(Loan loan) {
        this(loan.getId(), loan.getBorrowedAt(), loan.getReturnedAt(), loan.getActive(), BookCreateDTO.toBookCreateDTO(loan.getBook()), LibraryUserCreateDTO.toLibraryUserCreateDTO(loan.getLibraryUser()));
    }

    public static LoanCreateDTO toLoanCreateDTO(Loan loan) {
        return new LoanCreateDTO(loan);
    }

    public Loan toLoan() {
        var loan = new Loan();

        loan.setId(id);
        loan.setBorrowedAt(borrowedAt);
        loan.setReturnedAt(returnedAt);
        loan.setActive(active);
        loan.setBook(book.toBook());
        loan.setLibraryUser(libraryUser.toLibraryUser());

        return loan;
    }
}
