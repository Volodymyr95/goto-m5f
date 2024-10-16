package com.codegym.jrugotom5.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Advert> createdAdverts;
}
