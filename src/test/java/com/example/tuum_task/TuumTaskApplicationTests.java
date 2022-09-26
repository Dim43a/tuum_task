package com.example.tuum_task;

import com.example.tuum_task.rest.request.AccountRequest;
import com.example.tuum_task.rest.request.TransactionRequest;
import com.example.tuum_task.rest.response.AccountResponse;
import com.example.tuum_task.service.AccountService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

@SpringBootTest
class TuumTaskApplicationTests {

    @Autowired
    private AccountService service;

    @Test
    void createExistingAccountTest() {
        AccountRequest request = new AccountRequest();
        request.setCustomerId(1L);
        request.setCountry("Estonia");
        Map<String, Boolean> currencyList = new java.util.HashMap<>(Collections.emptyMap());
        currencyList.put("eur", true);
        request.setCurrencies(currencyList);
        try {
            service.createAccount(request);
        } catch (ResponseStatusException e) {
            MatcherAssert.assertThat(e.getReason(), Matchers.is("Account is existing"));
        }
    }

    @Test
    void createNewAccountTest () {
        AccountRequest request = new AccountRequest();

        request.setCustomerId(createRandomId());
        request.setCountry("Estonia");
        Map<String, Boolean> currencyList = new java.util.HashMap<>(Collections.emptyMap());
        currencyList.put("eur", true);
        request.setCurrencies(currencyList);

        AccountResponse responseFromService = service.createAccount(request);

        MatcherAssert.assertThat(responseFromService, Matchers.notNullValue());
    }

    @Test
    void createTransactionWithWrongCurrency() {

        TransactionRequest transactionRequest = prepareDebitTransactionRequest();
        transactionRequest.setCurrency("rub");
        try {
            service.transaction(transactionRequest);
        } catch (ResponseStatusException e) {

            MatcherAssert.assertThat(e.getReason(), Matchers.is("Wrong currency inserted"));
        }
    }

    @Test
    void createTransactionWithNotAllowedCurrency() {

        TransactionRequest transactionRequest = prepareDebitTransactionRequest();
        transactionRequest.setCurrency("usd");
        try {
            service.transaction(transactionRequest);
        } catch (ResponseStatusException e) {

            MatcherAssert.assertThat(e.getReason(), Matchers.is("Wrong currency inserted"));
        }
    }


    private Long createRandomId() {
        Random random = new Random();
        return random.nextLong(1, 999999999);
    }

    private TransactionRequest prepareDebitTransactionRequest() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(1L);
        transactionRequest.setAmount(new BigDecimal(1));
        transactionRequest.setCurrency("eur");
        transactionRequest.setDirection("IN");
        transactionRequest.setDescription("description");

        return transactionRequest;
    }
}
