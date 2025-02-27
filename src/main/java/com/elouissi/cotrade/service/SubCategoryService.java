package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.SubCategory;
import com.elouissi.cotrade.repository.SubCategoryRepository;
import com.elouissi.cotrade.service.DTO.SubCategoryDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.CategoryMapper;
import com.elouissi.cotrade.web.rest.VM.mapper.SubCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryService categoryService;
    private final SubCategoryMapper subCategoryMapper;

    public List<SubCategoryDTO> findAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryDTO)
                .collect(Collectors.toList());
    }

    public SubCategoryDTO findById(UUID id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        return subCategoryMapper.subCategoryToSubCategoryDTO(subCategory);
    }

    public SubCategoryDTO save(SubCategory subCategory) {
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.subCategoryToSubCategoryDTO(savedSubCategory);
    }

    public SubCategoryDTO update(UUID id, SubCategory subCategory) {
        SubCategory existingSubCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        existingSubCategory.setName(subCategory.getName());
        existingSubCategory.setDescription(subCategory.getDescription());
        existingSubCategory.setCategory(subCategory.getCategory());

        SubCategory updatedSubCategory = subCategoryRepository.save(existingSubCategory);
        return subCategoryMapper.subCategoryToSubCategoryDTO(updatedSubCategory);
    }

    @Transactional
    public void delete(UUID id) {
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));
        subCategoryRepository.delete(subCategory);
    }
}