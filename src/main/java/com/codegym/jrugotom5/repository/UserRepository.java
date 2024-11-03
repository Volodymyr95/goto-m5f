package com.codegym.jrugotom5.repository;

import com.codegym.jrugotom5.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    void deleteByEmail(String email);

    void deleteByFirstNameAndLastName(String firstName, String lastName);
}