package com.codegym.jrugotom5.repository;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AdvertRepository extends CrudRepository<Advert, Long> {

    boolean existsByTitle(String title);

    boolean existsByCreatedById(Long userId);

    List<Advert> findByDescriptionContaining(String description);

    List<Advert> findAllByCreatedDateBetween(LocalDate from, LocalDate to);

    List<Advert> findAllByTitleContainsIgnoreCase(String phrase);

    List<Advert> findAllByCategory(Category category);

    void deleteByTitle(String title);

    void deleteByCreatedById(Long userId);

    void deleteByDescriptionContaining(String description);

}
