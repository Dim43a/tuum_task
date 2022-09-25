package com.example.tuum_task.rest.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionRequest {
    private Long accountId;
    private BigDecimal amount;
    private String currency;
    private String direction;
    private String description;
}