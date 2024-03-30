package com.ang.rest.controllers;

import com.ang.rest.TestData;
import com.ang.rest.domain.entities.Transaction;
import com.ang.rest.services.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TransactionControllerIntegrationTests {

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    private TransactionService   transactionService;

    @Autowired
    public TransactionControllerIntegrationTests(MockMvc mockMvc, TransactionService transactionService)  {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.transactionService = transactionService;
    }

    @Test
    public void testThatCreateTransactionSuccessfullyReturnsHttp201Created() throws Exception {
        Transaction transaction = TestData.createTestTransactionA();
        transaction.setId(null);
        String stringJson = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stringJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()

        );
    }

    @Test
    public void testThatCreateTransactionSuccessfullyReturnsSavedTransaction() throws Exception {
        Transaction transaction = TestData.createTestTransactionA();
        transaction.setId(null);
        String transactionJson = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(transactionJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.shop").value("Masoutis")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.date").value("22/2/2024")

        );
    }

    @Test
    public void testThatGetTransactionsReturnsHttpStatus200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetTransactionsReturnsListOfTransactions() throws Exception {
        Transaction transaction = TestData.createTestTransactionA();
        transactionService.save(transaction);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].shop").value("Masoutis")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].date").value("22/2/2024")
        );
    }

    @Test
    public void testThatGetTransactionReturnsHttpStatus200WhenTransactionExists() throws Exception {
        Transaction transaction = TestData.createTestTransactionA();
        transactionService.save(transaction);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetTransactionReturnsHttpStatus400WhenTransactionNotExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/transactions/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    public void testThatGetTransactionReturnsTransaction() throws Exception {
        Transaction transaction = TestData.createTestTransactionA();
        transactionService.save(transaction);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/transactions/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.shop").value("Masoutis")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.date").value("22/2/2024")
        );
    }

}
