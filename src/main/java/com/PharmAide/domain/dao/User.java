package com.PharmAide.domain.dao;


import com.PharmAide.security.RoleUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements Serializable {

    @Id
    @Unique
    private String username;

    @JsonIgnore
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private RoleUser role;

    @Column(name = "fname", nullable = false)
    private String fname;

    @Column(name = "lname", nullable = false)
    private String lname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "lastlogin")
    private Instant lastlogin;

    @Column(name = "createdAt", nullable = false)
    @CreationTimestamp
    private final Instant createdAt = Instant.now().truncatedTo(ChronoUnit.DAYS);

}