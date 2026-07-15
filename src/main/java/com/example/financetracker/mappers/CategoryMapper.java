package com.example.financetracker.mapper;


import com.example.financetracker.dto.request.CategoryRequest;
import com.example.financetracker.dto.response.CategoryResponse;
import com.example.financetracker.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryRequest request) {
        return Category.builder().
                name(request.getName()).
                type(request.getType()).
                build();
    }

    public static CategoryResponse toRespone(CategoryResponse respone) {
        return CategoryResponse.builder().
                id(respone.getId()).
                name(respone.getName()).
                type(respone.getType()).
                createdAt(respone.getCreatedAt()).
                build();
    }
}
