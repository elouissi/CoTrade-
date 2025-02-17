package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Category;
import com.elouissi.cotrade.repository.CategoryRepository;
import com.elouissi.cotrade.service.DTO.CategoryDTO;
import com.elouissi.cotrade.web.rest.VM.CategoryVM;
import com.elouissi.cotrade.web.rest.VM.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<Category> findAll() {
        return categoryRepository.findAllWithSubCategories();
    }

    public CategoryDTO findById(UUID id) {
        Category category = categoryRepository.findByIdWithSubCategories(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.categoryToCategoryDTO(category);
    }
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public CategoryDTO update(UUID id, CategoryVM categoryVM) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(categoryVM.getName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.categoryToCategoryDTO(updatedCategory);
    }

    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }
}