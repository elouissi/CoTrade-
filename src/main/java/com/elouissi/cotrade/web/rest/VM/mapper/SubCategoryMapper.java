package com.elouissi.cotrade.web.rest.VM.mapper;

import com.elouissi.cotrade.domain.SubCategory;
import com.elouissi.cotrade.service.DTO.SubCategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    SubCategoryMapper INSTANCE = Mappers.getMapper(SubCategoryMapper.class);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    SubCategoryDTO subCategoryToSubCategoryDTO(SubCategory subCategory);

    @Mapping(target = "category.id", source = "categoryId")
    SubCategory subCategoryDTOToSubCategory(SubCategoryDTO subCategoryDTO);
}