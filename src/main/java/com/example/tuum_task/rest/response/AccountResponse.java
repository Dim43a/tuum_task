package com.example.tuum_task.rest.response;


import com.example.tuum_task.model.Balance;
import java.util.List;

public record AccountResponse (Long accountId,
     Long customerId,
     String country,
     List<Balance> listOfBalances
){}