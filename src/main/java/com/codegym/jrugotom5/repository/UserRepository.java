package com.codegym.jrugotom5.repository;

import com.codegym.jrugotom5.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}