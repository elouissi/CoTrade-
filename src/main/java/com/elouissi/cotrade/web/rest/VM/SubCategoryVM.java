package com.elouissi.cotrade.web.rest.VM;

import lombok.Data;
import java.util.UUID;

@Data
public class SubCategoryVM {
    private UUID id;
    private String name;
    private String description;
    private UUID categoryId;
}