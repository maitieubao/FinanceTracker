package com.example.financetracker.repository;

import com.example.financetracker.entity.Transaction;
import com.example.financetracker.Enum.TransactionType;
import org.springframework.beans.factory.aot.AotServices;
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
        (:type is NULL or t.type = :type) AND
        (:categoryId is NULL or t.categoryId = :categoryId) AND
        (:fromDate is NULL or t.fromDate >= :fromDate) AND
        (:toDate is NULL or r.toDate <= :toDate)
    """)
    List<Transaction> findWithFilter(
            @Param("type") TransactionType type,
            @Param("categoryId") Long categoryId,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
}

