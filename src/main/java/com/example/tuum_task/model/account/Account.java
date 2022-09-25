package com.example.tuum_task.model.account;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Column(name = "account_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(nullable = false)
    private String country;

    /*It looks not accurate but in this case it's the best solution.
    Because if customers later will decide to use another currency
    we can easily provide this opportunity.
     */
    private Boolean eur = false;
    private BigDecimal eurAmount = new BigDecimal("0.0");
    private Boolean sek = false;
    private BigDecimal sekAmount = new BigDecimal("0.0");
    private Boolean gpb = false;
    private BigDecimal gbpAmount = new BigDecimal("0.0");
    private Boolean usd = false;
    private BigDecimal usdAmount = new BigDecimal("0.0");
}