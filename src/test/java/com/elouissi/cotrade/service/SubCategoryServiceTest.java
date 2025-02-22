package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Category;
import com.elouissi.cotrade.domain.SubCategory;
import com.elouissi.cotrade.repository.SubCategoryRepository;
import com.elouissi.cotrade.service.DTO.SubCategoryDTO;
import com.elouissi.cotrade.web.rest.VM.mapper.SubCategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubCategoryServiceTest {

    @Mock
    private SubCategoryRepository subCategoryRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private SubCategoryMapper subCategoryMapper;

    @InjectMocks
    private SubCategoryService subCategoryService;

    private SubCategory subCategory;
    private SubCategoryDTO subCategoryDTO;
    private Category category;
    private UUID subCategoryId;

    @BeforeEach
    void setUp() {
        subCategoryId = UUID.randomUUID();

        category = new Category();
        category.setId(UUID.randomUUID());
        category.setName("Electronics");

        subCategory = new SubCategory();
        subCategory.setId(subCategoryId);
        subCategory.setName("Smartphones");
        subCategory.setDescription("Mobile phones and accessories");
        subCategory.setCategory(category);

        subCategoryDTO = new SubCategoryDTO();
        subCategoryDTO.setId(subCategoryId);
        subCategoryDTO.setName("Smartphones");
        subCategoryDTO.setDescription("Mobile phones and accessories");
    }

    @Test
    void findAll_ReturnsAllSubCategories() {
        // Arrange
        List<SubCategory> subCategories = Arrays.asList(subCategory);
        when(subCategoryRepository.findAll()).thenReturn(subCategories);
        when(subCategoryMapper.subCategoryToSubCategoryDTO(any(SubCategory.class))).thenReturn(subCategoryDTO);

        // Act
        List<SubCategoryDTO> result = subCategoryService.findAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Smartphones");
        verify(subCategoryRepository).findAll();
    }

    @Test
    void findById_ExistingId_ReturnsSubCategory() {
        // Arrange
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));
        when(subCategoryMapper.subCategoryToSubCategoryDTO(subCategory)).thenReturn(subCategoryDTO);

        // Act
        SubCategoryDTO result = subCategoryService.findById(subCategoryId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(subCategoryId);
        assertThat(result.getName()).isEqualTo("Smartphones");
        verify(subCategoryRepository).findById(subCategoryId);
    }

    @Test
    void findById_NonExistingId_ThrowsException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(subCategoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> subCategoryService.findById(nonExistingId));
        verify(subCategoryRepository).findById(nonExistingId);
    }

    @Test
    void save_ValidSubCategory_Success() {
        // Arrange
        when(subCategoryRepository.save(subCategory)).thenReturn(subCategory);
        when(subCategoryMapper.subCategoryToSubCategoryDTO(subCategory)).thenReturn(subCategoryDTO);

        // Act
        SubCategoryDTO result = subCategoryService.save(subCategory);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(subCategoryId);
        verify(subCategoryRepository).save(subCategory);
    }

    @Test
    void update_ExistingSubCategory_Success() {
        // Arrange
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));
        when(subCategoryRepository.save(any(SubCategory.class))).thenReturn(subCategory);
        when(subCategoryMapper.subCategoryToSubCategoryDTO(any(SubCategory.class))).thenReturn(subCategoryDTO);

        // Act
        SubCategoryDTO result = subCategoryService.update(subCategoryId, subCategory);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(subCategoryId);
        verify(subCategoryRepository).findById(subCategoryId);
        verify(subCategoryRepository).save(any(SubCategory.class));
    }

    @Test
    void update_NonExistingSubCategory_ThrowsException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(subCategoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> subCategoryService.update(nonExistingId, subCategory));
        verify(subCategoryRepository).findById(nonExistingId);
        verify(subCategoryRepository, never()).save(any(SubCategory.class));
    }

    @Test
    void delete_ExistingSubCategory_Success() {
        // Arrange
        when(subCategoryRepository.findById(subCategoryId)).thenReturn(Optional.of(subCategory));
        doNothing().when(subCategoryRepository).delete(subCategory);

        // Act
        subCategoryService.delete(subCategoryId);

        // Assert
        verify(subCategoryRepository).findById(subCategoryId);
        verify(subCategoryRepository).delete(subCategory);
    }

    @Test
    void delete_NonExistingSubCategory_ThrowsException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(subCategoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> subCategoryService.delete(nonExistingId));
        verify(subCategoryRepository).findById(nonExistingId);
        verify(subCategoryRepository, never()).delete(any());
    }
}