package com.veda.server.repository;

import com.veda.server.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {
    Page<Card> findAllByCategoryId(Integer categoryId, Pageable pageable);
}