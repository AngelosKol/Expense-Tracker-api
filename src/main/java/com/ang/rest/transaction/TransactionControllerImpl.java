package com.ang.rest.transaction;

import com.ang.rest.domain.dto.*;
import com.ang.rest.domain.entity.*;
import com.ang.rest.mapper.impl.TransactionMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
public class TransactionControllerImpl implements TransactionController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDto) {
        Transaction savedTransaction = transactionService.save(transactionDto);
        return new ResponseEntity<>(transactionMapper.mapToDto(savedTransaction), HttpStatus.CREATED);
    }
    @GetMapping(path = "/all")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.findAll();
    }

    @GetMapping
    public Page<TransactionDTO> getTransactions(Pageable pageable) {
        return transactionService.findAll(pageable);
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return  ResponseEntity.ok(transactionService.findOne(id));
    }

    @DeleteMapping(path = "/id/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}