package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.Category;
import com.elouissi.cotrade.domain.SubCategory;
import com.elouissi.cotrade.service.DTO.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "subCategories", expression = "java(mapSubCategoryNames(category))")
    CategoryDTO categoryToCategoryDTO(Category category);

    default List<String> mapSubCategoryNames(Category category) {
        if (category.getSubCategories() == null) {
            return new ArrayList<>();
        }
        return category.getSubCategories().stream()
                .filter(Objects::nonNull)
                .map(SubCategory::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "subCategories", expression = "java(mapSubCategories(categoryDTO))")
    Category categoryDTOToCategory(CategoryDTO categoryDTO);

    default List<SubCategory> mapSubCategories(CategoryDTO categoryDTO) {
        if (categoryDTO.getSubCategories() == null) {
            return new ArrayList<>();
        }
        return categoryDTO.getSubCategories()
                .stream()
                .map(name -> {
                    SubCategory subCategory = new SubCategory();
                    subCategory.setName(name);
                    return subCategory;
                })
                .collect(Collectors.toList());
    }
}