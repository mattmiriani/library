package com.api.library.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "library_user")
@Data
public class LibraryUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "number_phone", nullable = false)
    private String numberPhone;

    @Column(name = "active", nullable = false)
    private Boolean active = Boolean.TRUE;

    @OneToMany(mappedBy = "libraryUser", cascade = CascadeType.ALL)
    private List<Loan> loans = new ArrayList<>();

    public void mergeForUpdate(LibraryUser libraryUser) {
        this.name = libraryUser.getName();
        this.email = libraryUser.getEmail();
        this.numberPhone = libraryUser.getNumberPhone();
        this.active = libraryUser.getActive();
    }
}
