package com.example.tuum_task.service;

import com.example.tuum_task.mapper.AccountMapper;
import com.example.tuum_task.model.Account;
import com.example.tuum_task.model.Balance;
import com.example.tuum_task.model.Currencies;
import com.example.tuum_task.rest.request.AccountRequest;
import com.example.tuum_task.rest.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

@Service
public class AccountDefaultService implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        Account account = new Account();
        account.setCustomerId(request.getCustomerId());
        account.setCountry(request.getCountry());
        account.setEur(request.getEur());
        account.setSek(request.getSek());
        account.setGpb(request.getGbp());
        account.setUsd(request.getUsd());

        try {
            accountMapper.insertAccount(account);

            ArrayList<Balance> list = new ArrayList<>();

            Balance eurBalance = check(account.getEur(), Currencies.EUR);
            Balance sekBalance = check(account.getSek(), Currencies.SEK);
            Balance gbpBalance = check(account.getGpb(), Currencies.GBP);
            Balance usdBalance = check(account.getUsd(), Currencies.USD);

            list.add(eurBalance);
            list.add(sekBalance);
            list.add(gbpBalance);
            list.add(usdBalance);

            list.removeAll(Collections.singletonList(null));


//            if(account.getEur()) {
//                Balance balance = new Balance(Currencies.EUR, new BigDecimal(0));
//                list.add(balance);
//            }
//
//            if(account.getSek()) {
//                Balance balance = new Balance(Currencies.SEK, new BigDecimal(0));
//                list.add(balance);
//            }
//            if(account.getGpb()) {
//                Balance balance = new Balance(Currencies.GBP, new BigDecimal(0));
//                list.add(balance);
//            }
//            if(account.getUsd()) {
//                Balance balance = new Balance(Currencies.USD, new BigDecimal(0));
//                list.add(balance);
//            }

            Account accountId = accountMapper.selectAccount(account.getCustomerId());

            return new AccountResponse(
                    accountId.getAccountId(),
                    account.getCustomerId(),
                    account.getCountry(),
                    list
            );

        } catch(Exception e) {
            throw e;
        }
    }

    @Override
    public AccountResponse getAccount(Long accountId) {

        throw new RuntimeException("not implemented");
    }

    private Balance check(Boolean lala, Currencies currencies) {
        switch (currencies) {
            case EUR:
                if(lala) {
                    return new Balance(Currencies.EUR, new BigDecimal(0));
                } else {
                    break;
                }
            case SEK:
                if(lala) {
                    return new Balance(Currencies.SEK, new BigDecimal(0));
                } else {
                    break;
                }
            case GBP:
                if(lala) {
                    return new Balance(Currencies.GBP, new BigDecimal(0));
                } else {
                    break;
                }
            case USD:
                if(lala) {
                    return new Balance(Currencies.USD, new BigDecimal(0));
                } else {
                    break;
                }
            default:
                return null;
        }
        return null;
    }
}
