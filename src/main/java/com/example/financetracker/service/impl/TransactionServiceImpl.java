package com.example.financetracker.service.impl;

import com.example.financetracker.constants.TransactionType;
import com.example.financetracker.dtos.request.TransactionRequest;
import com.example.financetracker.dtos.response.SummaryResponse;
import com.example.financetracker.dtos.response.TransactionResponse;
import com.example.financetracker.entitys.Transaction;
import com.example.financetracker.mappers.TransactionMapper;
import com.example.financetracker.repository.TransactionRepository;
import com.example.financetracker.service.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        Transaction transaction = transactionMapper.toEntity(request);
        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(savedTransaction);
    }

    @Override
    public List<TransactionResponse> getAllTransactions(String type, Long categoryId, LocalDate fromDate, LocalDate toDate) {
        TransactionType transactionType = type != null ? TransactionType.valueOf(type) : null;

        List<Transaction> transactions = transactionRepository.findWithFilter(transactionType, categoryId,fromDate,toDate);

        return transactions.stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionResponse getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction with id " + id + " was not exist"));
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionResponse updateTransaction(Long id, TransactionRequest request) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction with id " + id + " was not exist"));

        transaction.setType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setTransactionDate(request.getTransactionDate());

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toResponse(savedTransaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        if(!transactionRepository.existsById(id)) {
            throw new EntityNotFoundException("Transaction with id " + id + " was not found");
        }
        transactionRepository.deleteById(id);
    }

    @Override
    public SummaryResponse getSummary() {
        List<Transaction> allTransactions = transactionRepository.findAll();

        BigDecimal totalIncome = allTransactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = allTransactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = totalIncome.subtract(totalExpense);

        return SummaryResponse.builder()
                .totalIncome(totalIncome)
                .totalExpense(totalExpense)
                .balance(balance)
                .build();
    }
}
