package com.elouissi.cotrade.web.rest.controller;

import com.elouissi.cotrade.domain.Category;
import com.elouissi.cotrade.domain.SubCategory;
import com.elouissi.cotrade.service.SubCategoryService;
import com.elouissi.cotrade.web.rest.VM.SubCategoryVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/V1/subcategories")
@RequiredArgsConstructor
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @GetMapping
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        return ResponseEntity.ok(subCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubCategory> getSubCategory(@PathVariable UUID id) {
        return ResponseEntity.ok(subCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategoryVM subCategoryVM) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryVM.getName());
        subCategory.setDescription(subCategoryVM.getDescription());

        Category category = new Category();
        category.setId(subCategoryVM.getCategoryId());
        subCategory.setCategory(category);

        return ResponseEntity.ok(subCategoryService.save(subCategory));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable UUID id, @RequestBody SubCategoryVM subCategoryVM) {
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryVM.getName());
        subCategory.setDescription(subCategoryVM.getDescription());

        Category category = new Category();
        category.setId(subCategoryVM.getCategoryId());
        subCategory.setCategory(category);

        return ResponseEntity.ok(subCategoryService.update(id, subCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable UUID id) {
        subCategoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}