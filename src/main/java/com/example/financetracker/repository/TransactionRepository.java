package com.example.financetracker.repository;

import com.example.financetracker.entitys.Transaction;
import com.example.financetracker.constants.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByType(TransactionType type);

    List<Transaction> findByCategoryId(Long categoryId);

    List<Transaction> findByTransactionDateBetween(LocalDate fromDate, LocalDate toDate);

    @Query("""
        SELECT t FROM Transaction t WHERE
        (:type is null or t.type = :type) AND
        (:categoryId is null or t.category.id = :categoryId) AND
        (:fromDate is null or t.transactionDate >= :fromDate) AND
        (:toDate is null or t.transactionDate <= :toDate)
    """)
    List<Transaction> findWithFilter(
            @Param("type") TransactionType type,
            @Param("categoryId") Long categoryId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}

