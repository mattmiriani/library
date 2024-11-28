package com.api.library.application.controller;

import com.api.library.application.dto.LoanCreateDTO;
import com.api.library.application.dto.LoanDTO;
import com.api.library.config.LibraryException;
import com.api.library.domain.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LoanController {

    private final LoanService loanService;

    @GetMapping
    public List<LoanDTO> findAll(@RequestParam(name = "libraryUserId", required = false) UUID libraryUserId) {
        if (isNull(libraryUserId)) {
            throw new LibraryException(HttpStatus.BAD_REQUEST, "Parameter 'libraryUserId' is required!");
        }

        return LoanDTO.toLoansDTO(loanService.findAll(libraryUserId));
    }

    @GetMapping("/{loanId}")
    public LoanDTO findById(@PathVariable UUID loanId) {
        return LoanDTO.toLoanDTO(loanService.findById(loanId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoanCreateDTO create(@RequestBody LoanCreateDTO loanCreateDTO) {
        return LoanCreateDTO.toLoanCreateDTO(loanService.create(loanCreateDTO.toLoan()));
    }

    @PutMapping("/{loanId}")
    public LoanDTO update(@PathVariable UUID loanId) {
        return LoanDTO.toLoanDTO(loanService.update(loanId));
    }
}
