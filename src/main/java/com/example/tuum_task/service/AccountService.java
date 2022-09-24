package com.example.tuum_task.service;

import com.example.tuum_task.model.transaction.Transaction;
import com.example.tuum_task.rest.request.AccountRequest;
import com.example.tuum_task.rest.request.TransactionRequest;
import com.example.tuum_task.rest.response.AccountResponse;
import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccount(Long accountId);

    Transaction transaction(TransactionRequest request);

    List<Transaction> getTransactionList(Long accountId);
}