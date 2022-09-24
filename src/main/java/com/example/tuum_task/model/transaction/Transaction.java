package com.example.tuum_task.model.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @Column(name = "account_id")
    private Long accountId;

    private BigDecimal amount;
    private String currency;

    private String direction;
    private String description;

    @Column(name = "left_balance")
    private BigDecimal leftBalance;

    private LocalDateTime date;

    public Transaction(Long transactionId, Long accountId, BigDecimal amount, String currency, String direction, String description, BigDecimal leftBalance) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.amount = amount;
        this.currency = currency;
        this.direction = direction;
        this.description = description;
        this.leftBalance = leftBalance;
    }
}