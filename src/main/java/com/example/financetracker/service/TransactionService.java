package com.example.financetracker.service;

import com.example.financetracker.dtos.request.TransactionRequest;
import com.example.financetracker.dtos.response.SummaryResponse;
import com.example.financetracker.dtos.response.TransactionResponse;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
    List<TransactionResponse> getAllTransactions(String type, Long categoryId, LocalDate fromDate, LocalDate toDate);
    TransactionResponse getTransactionById(Long id);
    TransactionResponse updateTransaction(Long id, TransactionRequest request);
    void deleteTransaction(Long id);
    SummaryResponse getSummary();
}
