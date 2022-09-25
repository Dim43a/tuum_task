package com.example.tuum_task.model.account;

import java.math.BigDecimal;

public record Balance (Currencies currency, BigDecimal amount){}