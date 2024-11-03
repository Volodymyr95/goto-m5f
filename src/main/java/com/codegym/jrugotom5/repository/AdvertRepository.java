package com.codegym.jrugotom5.repository;

import com.codegym.jrugotom5.entity.Advert;
import com.codegym.jrugotom5.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AdvertRepository extends CrudRepository<Advert, Long> {
    List<Advert> findAllByCreatedDateBetween(LocalDate from, LocalDate to);

    List<Advert> findAllByTitleContainsIgnoreCase(String phrase);

    List<Advert> findAllByCategory(Category category);

    @Modifying
    @Query("UPDATE Advert a SET a.isActive = false WHERE a.isActive = true AND a.endDate < CURRENT_DATE")
    void deactivateExpiredAdverts();
}
