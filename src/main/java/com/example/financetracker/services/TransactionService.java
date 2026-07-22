package com.example.financetracker.services;

import com.example.financetracker.dtos.transaction.TransactionRequest;
import com.example.financetracker.dtos.response.SummaryResponse;
import com.example.financetracker.dtos.transaction.TransactionResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
    Page<TransactionResponse> getAllTransactions(String type, Long categoryId, LocalDate fromDate, LocalDate toDate, Pageable pageable);
    TransactionResponse getTransactionById(Long id);
    TransactionResponse updateTransaction(Long id, TransactionRequest request);
    void deleteTransaction(Long id);
    SummaryResponse getSummary(LocalDate fromDate, LocalDate toDate);
}
