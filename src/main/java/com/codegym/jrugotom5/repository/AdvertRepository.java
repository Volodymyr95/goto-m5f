package com.codegym.jrugotom5.repository;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AdvertRepository extends CrudRepository<Advert, Long> {
    List<Advert> findAllByCreatedDateBetween(LocalDate from, LocalDate to);
    List<Advert> findAllByCategory (Category category);
}
