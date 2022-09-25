package com.example.tuum_task.rest.request;

import lombok.Data;
import java.util.Map;

@Data
public class AccountRequest {
    private Long customerId;
    private String country;
    private Map<String, Boolean> currencies;
}
