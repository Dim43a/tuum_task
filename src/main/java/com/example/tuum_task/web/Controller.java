package com.example.tuum_task.web;

import com.example.tuum_task.mapper.AccountMapper;
import com.example.tuum_task.model.Account;
import com.example.tuum_task.rest.request.AccountRequest;
import com.example.tuum_task.rest.response.AccountResponse;
import com.example.tuum_task.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tuum")
public class Controller {

    @Autowired
    private AccountMapper accountMapper;


    private AccountService accountService;



    @PostMapping("/account")
    public AccountResponse createUser(@RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }

    @PostMapping("/getAccount/{accountId}")
    public AccountResponse getUser(@PathVariable("accountId") Long accountId) {

        throw new RuntimeException();
    }
}
