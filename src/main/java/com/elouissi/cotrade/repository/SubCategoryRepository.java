package com.elouissi.cotrade.repository;

import com.elouissi.cotrade.domain.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
}
