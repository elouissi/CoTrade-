package com.elouissi.cotrade.web.rest.VM;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryVM {
    private UUID id;
    private String name;
    private List<SubCategoryVM> subCategories;
}