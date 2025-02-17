package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.SubCategory;
import com.elouissi.cotrade.repository.SubCategoryRepository;
import com.elouissi.cotrade.web.rest.VM.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryService categoryService;
    private CategoryMapper categoryMapper;

    public List<SubCategory> findAll() {
        return subCategoryRepository.findAll();
    }

    public SubCategory findById(UUID id) {
        return subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
    }

    public SubCategory save(SubCategory subCategory) {
        subCategory.setCategory(categoryMapper.categoryDTOToCategory(categoryService.findById(subCategory.getCategory().getId())));
        return subCategoryRepository.save(subCategory);
    }

    public SubCategory update(UUID id, SubCategory subCategory) {
        SubCategory existingSubCategory = findById(id);
        existingSubCategory.setName(subCategory.getName());
        existingSubCategory.setDescription(subCategory.getDescription());
        existingSubCategory.setCategory(categoryMapper.categoryDTOToCategory(categoryService.findById(subCategory.getCategory().getId())));
        return subCategoryRepository.save(existingSubCategory);
    }

    public void delete(UUID id) {
        subCategoryRepository.deleteById(id);
    }
}