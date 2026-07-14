package com.example.financetracker.mapper;

import com.example.financetracker.dto.request.TransactionRequest;
import com.example.financetracker.dto.response.TransactionResponse;
import com.example.financetracker.entity.Category;
import com.example.financetracker.entity.Transaction;
import com.example.financetracker.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionMapper {

    private final CategoryRepository categoryRepository;

    public Transaction toEntity(TransactionRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId()).
                                orElseThrow(() -> new IllegalArgumentException("Id cannot be null"));

        return Transaction.builder()
                .amount(request.getAmount())
                .type(request.getType())
                .description(request.getDescription())
                .transactionDate(request.getTransactionDate())
                .category(category)
                .build();
    }

    public TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .description(transaction.getDescription())
                .transactionDate(transaction.getTransactionDate())
                .categoryId(transaction.getCategory().getId())
                .categoryName(transaction.getCategory().getName())
                .createdAt(transaction.getCreateAt())
                .updatedAt(transaction.getUpdateAt())
                .build();
    }
}