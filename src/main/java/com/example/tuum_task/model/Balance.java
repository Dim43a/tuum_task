package com.example.tuum_task.model;

import java.math.BigDecimal;

public record Balance (
        Currencies currency,
        BigDecimal amount
){}