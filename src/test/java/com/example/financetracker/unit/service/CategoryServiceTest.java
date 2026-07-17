package com.example.financetracker.unit.service;

import com.example.financetracker.constants.TransactionType;
import com.example.financetracker.dtos.category.CategoryRequest;
import com.example.financetracker.dtos.category.CategoryResponse;
import com.example.financetracker.entitys.Category;
import com.example.financetracker.mappers.CategoryMapper;
import com.example.financetracker.repositories.CategoryRepository;
import com.example.financetracker.services.impl.CategoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private CategoryRequest mockCategoryRequest;
    private CategoryResponse mockCategoryResponse;
    private Category mockCategory;

    @BeforeEach
    void setUp() {
        mockCategory = Category.builder()
                .id(1L)
                .name("Food")
                .type(TransactionType.EXPENSE)
                .createdAt(LocalDateTime.now())
                .transactions(new ArrayList<>())
                .build();
        mockCategoryRequest = CategoryRequest.builder()
                .type(TransactionType.EXPENSE)
                .name("Food")
                .build();
        mockCategoryResponse = CategoryResponse.builder()
                .id(1L)
                .name("Food")
                .type(TransactionType.EXPENSE)
                .createdAt(mockCategory.getCreatedAt())
                .build();
    }

    @Test
    void givenCategoryRequest_whenCreateCategory_thenCreateNewCategory() {
        // Arrange
        given(categoryMapper.toEntity(mockCategoryRequest)).willReturn(mockCategory);
        given(categoryRepository.save(mockCategory)).willReturn(mockCategory);
        given(categoryMapper.toResponse(mockCategory)).willReturn(mockCategoryResponse);

        // Act
        CategoryResponse result = categoryService.createCategory(mockCategoryRequest);

        // Assert
        assertNotNull(result);
        assertEquals(mockCategoryResponse, result);
//        assertEquals(mockCategoryResponse.getId(), result.getId());
//        assertEquals(mockCategoryResponse.getName(), result.getName());
//        assertEquals(mockCategoryResponse.getType(), result.getType());

        verify(categoryMapper).toEntity(mockCategoryRequest);
        verify(categoryRepository).save(mockCategory);
        verify(categoryMapper).toResponse(mockCategory);
    }

    @Test
    void givenNothing_whenGetAllCategory_thenReturnCategoryList() {
        // Arrange
        Category cat1 = Category.builder().id(1L).name("Tiền điện").type(TransactionType.EXPENSE).build();
        Category cat2 = Category.builder().id(2L).name("Lương").type(TransactionType.INCOME).build();
        Category cat3 = Category.builder().id(3L).name("Ăn uống").type(TransactionType.EXPENSE).build();
        List<Category> categoryList = List.of(cat1, cat2, cat3);

        CategoryResponse res1 = CategoryResponse.builder().id(1L).name("Tiền điện").type(TransactionType.EXPENSE).build();
        CategoryResponse res2 = CategoryResponse.builder().id(2L).name("Lương").type(TransactionType.INCOME).build();
        CategoryResponse res3 = CategoryResponse.builder().id(3L).name("Ăn uống").type(TransactionType.EXPENSE).build();

        given(categoryRepository.findAll()).willReturn(categoryList);
        given(categoryMapper.toResponse(cat1)).willReturn(res1);
        given(categoryMapper.toResponse(cat2)).willReturn(res2);
        given(categoryMapper.toResponse(cat3)).willReturn(res3);

        // Act
        List<CategoryResponse> actualResponse = categoryService.getAllCategory();

        // Assert
        assertNotNull(actualResponse);
        assertEquals(3, actualResponse.size());
        assertEquals("Tiền điện", actualResponse.get(0).getName());
        assertEquals("Lương", actualResponse.get(1).getName());
        assertEquals("Ăn uống", actualResponse.get(2).getName());
        verify(categoryRepository).findAll();
        verify(categoryMapper, times(3)).toResponse(any(Category.class));
    }

    @Test
    void givenExistingId_whenGetCategoryById_thenReturnCategory() {
        //Assert
        Long categoryId = 2L;
        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(mockCategory));
        given(categoryMapper.toResponse(mockCategory)).willReturn(mockCategoryResponse);

        //Act
        CategoryResponse result = categoryService.getCategoryById(categoryId);

        //Assert
        assertNotNull(result);
        assertEquals(result.getId(), mockCategoryResponse.getId());

        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper).toResponse(mockCategory);
    }

    @Test
    void givenNonExistingId_whenGetCategoryById_thenThrowEntityNotFoundException() {
        //Assert
        Long categoryId = 2L;
        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        //Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.getCategoryById(categoryId);
        });

        assertEquals("Category with id " + categoryId + " was not found", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(categoryMapper, never()).toResponse(any());
    }

    @Test
    void givenExistingIdAndRequest_whenUpdateCategory_thenReturnUpdatedCategory() {
        // Arrange
        Long categoryId = 1L;
        CategoryRequest updateRequest = CategoryRequest.builder()
                .name("Updated Food")
                .type(TransactionType.EXPENSE)
                .build();
        
        Category existingCategory = Category.builder()
                .id(1L)
                .name("Food")
                .type(TransactionType.EXPENSE)
                .createdAt(mockCategory.getCreatedAt())
                .transactions(new ArrayList<>())
                .build();

        Category updatedCategory = Category.builder()
                .id(1L)
                .name("Updated Food")
                .type(TransactionType.EXPENSE)
                .createdAt(mockCategory.getCreatedAt())
                .transactions(new ArrayList<>())
                .build();

        CategoryResponse updatedResponse = CategoryResponse.builder()
                .id(1L)
                .name("Updated Food")
                .type(TransactionType.EXPENSE)
                .createdAt(mockCategory.getCreatedAt())
                .build();

        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(existingCategory));
        given(categoryRepository.save(existingCategory)).willReturn(updatedCategory);
        given(categoryMapper.toResponse(updatedCategory)).willReturn(updatedResponse);

        // Act
        CategoryResponse result = categoryService.updateCategory(categoryId, updateRequest);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Food", result.getName());
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository).save(existingCategory);
        verify(categoryMapper).toResponse(updatedCategory);
    }

    @Test
    void givenNonExistingIdAndRequest_whenUpdateCategory_thenThrowEntityNotFoundException() {
        // Arrange
        Long categoryId = 2L;
        CategoryRequest updateRequest = CategoryRequest.builder()
                .name("Updated Food")
                .type(TransactionType.EXPENSE)
                .build();
        given(categoryRepository.findById(categoryId)).willReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.updateCategory(categoryId, updateRequest);
        });
        assertEquals("Category with id " + categoryId + " was not found", exception.getMessage());
        verify(categoryRepository).findById(categoryId);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    void givenExistingId_whenDeleteCategory_thenDeleteSuccessfully() {
        // Arrange
        Long categoryId = 1L;
        given(categoryRepository.existsById(categoryId)).willReturn(true);
        doNothing().when(categoryRepository).deleteById(categoryId);

        // Act
        assertDoesNotThrow(() -> categoryService.deleteCategory(categoryId));

        // Assert
        verify(categoryRepository).existsById(categoryId);
        verify(categoryRepository).deleteById(categoryId);
    }

    @Test
    void givenNonExistingId_whenDeleteCategory_thenThrowEntityNotFoundException() {
        // Arrange
        Long categoryId = 2L;
        given(categoryRepository.existsById(categoryId)).willReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });
        assertEquals("Category with id " + categoryId + " was not found", exception.getMessage());
        verify(categoryRepository).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(any());
    }
}
