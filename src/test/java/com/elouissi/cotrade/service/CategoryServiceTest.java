package com.elouissi.cotrade.service;

import com.elouissi.cotrade.domain.Category;
import com.elouissi.cotrade.repository.CategoryRepository;
import com.elouissi.cotrade.service.DTO.CategoryDTO;
import com.elouissi.cotrade.web.rest.VM.CategoryVM;
import com.elouissi.cotrade.web.rest.VM.mapper.CategoryMapper;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryDTO categoryDTO;
    private CategoryVM categoryVM;
    private UUID categoryId;

    @BeforeEach
    void setUp() {
        categoryId = UUID.randomUUID();

        category = new Category();
        category.setId(categoryId);
        category.setName("Electronics");

        categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setName("Electronics");
        categoryDTO.setSubCategories(Arrays.asList("Phones", "Computers"));

        categoryVM = new CategoryVM();
        categoryVM.setName("Updated Electronics");
    }

    @Test
    void findAll_ReturnsAllCategories() {
        // Arrange
        List<Category> categories = Arrays.asList(category);
        when(categoryRepository.findAllWithSubCategories()).thenReturn(categories);

        // Act
        List<Category> result = categoryService.findAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Electronics");
        verify(categoryRepository).findAllWithSubCategories();
    }

    @Test
    void findById_ExistingId_ReturnsCategory() {
        // Arrange
        when(categoryRepository.findByIdWithSubCategories(categoryId)).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryDTO(category)).thenReturn(categoryDTO);

        // Act
        CategoryDTO result = categoryService.findById(categoryId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(categoryId);
        assertThat(result.getName()).isEqualTo("Electronics");
        verify(categoryRepository).findByIdWithSubCategories(categoryId);
    }

    @Test
    void findById_NonExistingId_ThrowsException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(categoryRepository.findByIdWithSubCategories(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoryService.findById(nonExistingId));
        verify(categoryRepository).findByIdWithSubCategories(nonExistingId);
    }

    @Test
    void save_ValidCategory_Success() {
        // Arrange
        when(categoryRepository.save(category)).thenReturn(category);

        // Act
        Category result = categoryService.save(category);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(categoryId);
        verify(categoryRepository).save(category);
    }



    @Test
    void update_NonExistingCategory_ThrowsException() {
        // Arrange
        UUID nonExistingId = UUID.randomUUID();
        when(categoryRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> categoryService.update(nonExistingId, categoryVM));
        verify(categoryRepository).findById(nonExistingId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void delete_ExistingCategory_Success() {
        // Arrange
        doNothing().when(categoryRepository).deleteById(categoryId);

        // Act
        categoryService.delete(categoryId);

        // Assert
        verify(categoryRepository).deleteById(categoryId);
    }
}