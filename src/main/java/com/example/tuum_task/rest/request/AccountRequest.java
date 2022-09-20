package com.example.tuum_task.rest.request;

import lombok.Data;

@Data
public class AccountRequest {
    private Long customerId;
    private String country;

    private Boolean eur = false;
    private Boolean sek = false;
    private Boolean gbp = false;
    private Boolean usd = false;
}
