package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.Category;
import com.elouissi.cotrade.domain.SubCategory;
import com.elouissi.cotrade.service.CategoryService;
import com.elouissi.cotrade.service.DTO.CategoryDTO;
import com.elouissi.cotrade.service.DTO.SubCategoryDTO;
import com.elouissi.cotrade.service.SubCategoryService;
import com.elouissi.cotrade.web.rest.VM.SubCategoryVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@RestController
@RequestMapping("/api/V1/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<SubCategoryDTO>> getAllSubCategories() {
        return ResponseEntity.ok(subCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategoryDTO> getSubCategory(@PathVariable UUID id) {
        return ResponseEntity.ok(subCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubCategoryDTO> createSubCategory(@RequestBody SubCategoryVM subCategoryVM) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryVM.getName());
        subCategory.setDescription(subCategoryVM.getDescription());

        CategoryDTO categoryDTO = categoryService.findById(subCategoryVM.getCategoryId());
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());

        subCategory.setCategory(category);

        return ResponseEntity.ok(subCategoryService.save(subCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubCategoryDTO> updateSubCategory(@PathVariable UUID id, @RequestBody SubCategoryVM subCategoryVM) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryVM.getName());
        subCategory.setDescription(subCategoryVM.getDescription());

        CategoryDTO categoryDTO = categoryService.findById(subCategoryVM.getCategoryId());
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());

        subCategory.setCategory(category);

        return ResponseEntity.ok(subCategoryService.update(id, subCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubCategory(@PathVariable UUID id) {
        try {
            subCategoryService.delete(id);
            Map<String, String> message = new HashMap<>();
            message.put("message", "La sous-catégorie a été supprimée avec succès");
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(404).body(error);
        }
    }
}