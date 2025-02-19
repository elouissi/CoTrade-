package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.Category;
import com.elouissi.cotrade.service.CategoryService;
import com.elouissi.cotrade.service.DTO.CategoryDTO;
import com.elouissi.cotrade.web.rest.VM.CategoryVM;
import com.elouissi.cotrade.web.rest.VM.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/V1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        List<CategoryDTO> categoryDTOs = categories.stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryVM categoryVM) {
        Category category = new Category();
        category.setName(categoryVM.getName());
        Category savedCategory = categoryService.save(category);
        return ResponseEntity.ok(categoryMapper.categoryToCategoryDTO(savedCategory));
    }


    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable UUID id, @RequestBody CategoryVM categoryVM) {
        return ResponseEntity.ok(categoryService.update(id, categoryVM));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        categoryService.delete(id);
        Map<String,String> message = new HashMap<>();
        message.put("message","the category has been deleted");
        return ResponseEntity.ok(message);
    }
}