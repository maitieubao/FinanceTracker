package com.example.financetracker.mappers;


import com.example.financetracker.dtos.request.CategoryRequest;
import com.example.financetracker.dtos.response.CategoryResponse;
import com.example.financetracker.entitys.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        return Category.builder().
                name(request.getName()).
                type(request.getType()).
                build();
    }

    public CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder().
                id(category.getId()).
                name(category.getName()).
                type(category.getType()).
                createdAt(category.getCreatedAt()).
                build();
    }
}
