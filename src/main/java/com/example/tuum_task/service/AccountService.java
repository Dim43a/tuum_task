package com.example.tuum_task.service;

import com.example.tuum_task.rest.request.AccountRequest;
import com.example.tuum_task.rest.response.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(AccountRequest request);

    AccountResponse getAccount(Long accountId);
}