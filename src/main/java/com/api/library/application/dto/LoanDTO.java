package com.api.library.application.dto;

import com.api.library.domain.entity.Loan;

import java.time.LocalDateTime;
import java.util.List;

public record LoanDTO(LocalDateTime borrowedAt, LocalDateTime returnedAt, Boolean active, BookDTO book, LibraryUserDTO libraryUser) {

    public LoanDTO(Loan loan) {
        this(loan.getBorrowedAt(), loan.getReturnedAt(), loan.getActive(), BookDTO.toBookDTO(loan.getBook()), LibraryUserDTO.toLibraryUserDTO(loan.getLibraryUser()));
    }

    public static List<LoanDTO> toLoansDTO(List<Loan> loans) {
        return loans.stream().map(LoanDTO::new).toList();
    }

    public static LoanDTO toLoanDTO(Loan loan) {
        return new LoanDTO(loan);
    }

    public Loan toLoan() {
        var laon = new Loan();

        laon.setBorrowedAt(borrowedAt);
        laon.setReturnedAt(returnedAt);
        laon.setActive(active);
        laon.setBook(book.toBook());
        laon.setLibraryUser(libraryUser.toLibraryUser());

        return laon;
    }
}
