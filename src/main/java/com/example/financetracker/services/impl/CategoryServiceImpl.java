package com.example.financetracker.services.impl;

import com.example.financetracker.dtos.category.CategoryRequest;
import com.example.financetracker.dtos.category.CategoryResponse;
import com.example.financetracker.entitys.Category;
import com.example.financetracker.mappers.CategoryMapper;
import com.example.financetracker.repositories.CategoryRepository;
import com.example.financetracker.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = categoryMapper.toEntity(request);
        Category saveCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(saveCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " was not found"));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " was not found"));

        category.setName(request.getName());
        category.setType(request.getType());

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category with id " + id + " was not found");
        }
        categoryRepository.deleteById(id);
    }
}
