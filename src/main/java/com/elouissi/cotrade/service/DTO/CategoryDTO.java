package com.elouissi.cotrade.service.DTO;

import java.util.List;
import java.util.UUID;

public class CategoryDTO {
    private UUID id;
    private String name;
    private List<String> subCategories;

    public CategoryDTO() {}

    public CategoryDTO(UUID id, String name, List<String> subCategories) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
    }

    public CategoryDTO(UUID id, String updatedElectronics) {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<String> subCategories) {
        this.subCategories = subCategories;
    }
}
