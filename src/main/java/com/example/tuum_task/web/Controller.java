package com.example.tuum_task.web;

import com.example.tuum_task.model.transaction.Transaction;
import com.example.tuum_task.rest.request.AccountRequest;
import com.example.tuum_task.rest.request.TransactionRequest;
import com.example.tuum_task.rest.response.AccountResponse;
import com.example.tuum_task.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tuum")
public class Controller {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account")
    public AccountResponse createUser(@RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }

    @PostMapping("/getAccount/{accountId}")
    public AccountResponse getUser(@PathVariable("accountId") Long accountId) {
        return accountService.getAccount(accountId);
    }


    @GetMapping("/transaction")
    public Transaction transaction(@RequestBody TransactionRequest request) {
        return accountService.transaction(request);
    }

    @GetMapping("/transactionList/{accountId}")
    public List<Transaction> transactionList(@PathVariable Long accountId) {
        return accountService.getTransactionList(accountId);
    }
}
