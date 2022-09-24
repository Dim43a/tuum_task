package com.example.tuum_task.service;

import com.example.tuum_task.mapper.AccountMapper;
import com.example.tuum_task.mapper.TransactionMapper;
import com.example.tuum_task.model.account.Account;
import com.example.tuum_task.model.account.Balance;
import com.example.tuum_task.model.account.Currencies;
import com.example.tuum_task.model.transaction.Transaction;
import com.example.tuum_task.rest.request.AccountRequest;
import com.example.tuum_task.rest.request.TransactionRequest;
import com.example.tuum_task.rest.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.Collections;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class AccountDefaultService implements AccountService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    TransactionMapper transactionMapper;

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        Account account = prepareAccount(request);
        try{
            accountMapper.insertAccount(account);
            return createAccountResponse(account);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong");
        }
    }

    @Override
    public AccountResponse getAccount(Long accountId) {
        Account account = accountMapper.selectAccountByAccountId(accountId);

        return createAccountResponse(account);
    }

    @Override
    public Transaction transaction(TransactionRequest request){

        Account account = accountMapper.selectAccountByAccountId(request.getAccountId());

        switch (request.getCurrency()) {
            case "EUR" -> {
                if(account.getEur()) {

                    if(Objects.equals(request.getDirection(), "IN")) {
                        accountMapper.updateEur(request.getAccountId(), account.getEurAmount().add(request.getAmount()));

                    } else if (Objects.equals(request.getDirection(), "OUT")) {
                        if(account.getEurAmount().subtract(request.getAmount()).doubleValue() < 0) {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money on the account to complete transaction");
                        } else {
                            accountMapper.updateEur(request.getAccountId(), account.getEurAmount().subtract(request.getAmount()));
                        }

                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong direction, available IN and OUT");
                    }

                    Transaction eurTransaction = prepareTransaction(account, request, Currencies.EUR, request.getDirection());
                    transactionMapper.insetTransaction(eurTransaction);

                    List<Transaction> list = lala(request);
                    return new Transaction(
                            list.get(0).getTransactionId(),
                            request.getAccountId(),
                            request.getAmount(),
                            request.getCurrency(),
                            request.getDirection(),
                            request.getDescription(),
                            accountMapper.selectAccountByAccountId(request.getAccountId()).getEurAmount()
                    );
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected currency is not available for this account");
                }
            }
            case "GPB" -> {
                if(account.getGpb()) {
                    if(Objects.equals(request.getDirection(), "IN")) {
                        accountMapper.updateGbp(request.getAccountId(), account.getGbpAmount().add(request.getAmount()));
                    } else if (Objects.equals(request.getDirection(), "OUT")) {
                        accountMapper.updateGbp(request.getAccountId(), account.getGbpAmount().subtract(request.getAmount()));
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong direction, available IN and OUT");
                    }

                    Transaction gbpTransaction = prepareTransaction(account, request, Currencies.GBP, request.getDirection());
                    transactionMapper.insetTransaction(gbpTransaction);

                    List<Transaction> list = lala(request);
                    return new Transaction(
                            list.get(0).getTransactionId(),
                            request.getAccountId(),
                            request.getAmount(),
                            request.getCurrency(),
                            request.getDirection(),
                            request.getDescription(),
                            accountMapper.selectAccountByAccountId(request.getAccountId()).getGbpAmount()
                    );
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected currency is not available for this account");

                }
            }
            case "SEK" -> {
                if(account.getSek()) {
                    if(Objects.equals(request.getDirection(), "IN")) {
                        accountMapper.updateSek(request.getAccountId(), account.getSekAmount().add(request.getAmount()));
                    } else if (Objects.equals(request.getDirection(), "OUT")) {
                        accountMapper.updateSek(request.getAccountId(), account.getSekAmount().subtract(request.getAmount()));
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong direction, available IN and OUT");
                    }

                    Transaction sekTransaction = prepareTransaction(account, request, Currencies.SEK, request.getDirection());
                    transactionMapper.insetTransaction(sekTransaction);

                    List<Transaction> list = lala(request);
                    return new Transaction(
                            list.get(0).getTransactionId(),
                            request.getAccountId(),
                            request.getAmount(),
                            request.getCurrency(),
                            request.getDirection(),
                            request.getDescription(),
                            accountMapper.selectAccountByAccountId(request.getAccountId()).getSekAmount()
                    );
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected currency is not available for this account");
                }
            }
            case "USD" -> {
                if(account.getUsd()) {
                    if(Objects.equals(request.getDirection(), "IN")) {
                        accountMapper.updateUsd(request.getAccountId(), account.getUsdAmount().add(request.getAmount()));
                    } else if (Objects.equals(request.getDirection(), "OUT")) {
                        accountMapper.updateUsd(request.getAccountId(), account.getUsdAmount().subtract(request.getAmount()));
                    } else {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong direction, available IN and OUT");
                    }

                    Transaction usdTransaction = prepareTransaction(account, request, Currencies.USD, request.getDirection());
                    transactionMapper.insetTransaction(usdTransaction);

                    List<Transaction> list = lala(request);
                    return new Transaction(
                            list.get(0).getTransactionId(),
                            request.getAccountId(),
                            request.getAmount(),
                            request.getCurrency(),
                            request.getDirection(),
                            request.getDescription(),
                            accountMapper.selectAccountByAccountId(request.getAccountId()).getUsdAmount()
                    );
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selected currency is not available for this account");
                }
            }
        }


        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong currency inserted");
    }

    @Override
    public List<Transaction> getTransactionList(Long accountId) {

        return transactionMapper.getTransactionList(accountId);
    }

    private Account prepareAccount(AccountRequest request) {
        Account account = new Account();
        account.setCustomerId(request.getCustomerId());
        account.setCountry(request.getCountry());
        account.setEur(request.getEur());
        account.setSek(request.getSek());
        account.setGpb(request.getGbp());
        account.setUsd(request.getUsd());

        return account;
    }

    private List<Transaction> lala (TransactionRequest request) {
        List<Transaction> list = transactionMapper.getTransactionList(request.getAccountId());
        //creating comparator for proper date sorting
        Comparator<Transaction> comparator = (tran1, tran2) -> tran2.getDate().compareTo(tran1.getDate());
        list.sort(comparator);

        return list;
    }

    private AccountResponse createAccountResponse(Account account) {
        ArrayList<Balance> list = new ArrayList<>();
        Balance eurBalance = check(account.getEur(), Currencies.EUR);
        Balance sekBalance = check(account.getSek(), Currencies.SEK);
        Balance gbpBalance = check(account.getGpb(), Currencies.GBP);
        Balance usdBalance = check(account.getUsd(), Currencies.USD);

        list.add(eurBalance);
        list.add(sekBalance);
        list.add(gbpBalance);
        list.add(usdBalance);

        //Remove null elements from arrayList
        list.removeAll(Collections.singletonList(null));

        Account accountId = accountMapper.selectAccountByCustomerId(account.getCustomerId());

        return new AccountResponse(
                accountId.getAccountId(),
                account.getCustomerId(),
                account.getCountry(),
                list
        );
    }

    private Transaction prepareTransaction (Account account, TransactionRequest request, Currencies currencies, String action) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setCurrency(request.getCurrency());
        transaction.setDirection(request.getDirection());
        transaction.setDate(LocalDateTime.now());

        switch (currencies) {

            case EUR:
                if(Objects.equals(action, "IN")) {
                    transaction.setLeftBalance(account.getEurAmount().add(request.getAmount()));
                } else {
                    transaction.setLeftBalance(account.getEurAmount().subtract(request.getAmount()));
                }
                break;
            case GBP:
                if(Objects.equals(action, "IN")) {
                    transaction.setLeftBalance(account.getGbpAmount().add(request.getAmount()));
                } else {
                    transaction.setLeftBalance(account.getGbpAmount().subtract(request.getAmount()));
                }
                break;
            case SEK:
                if(Objects.equals(action, "IN")) {
                    transaction.setLeftBalance(account.getSekAmount().add(request.getAmount()));
                } else {
                    transaction.setLeftBalance(account.getSekAmount().subtract(request.getAmount()));
                }
                break;
            case USD:
                if(Objects.equals(action, "IN")) {
                    transaction.setLeftBalance(account.getUsdAmount().add(request.getAmount()));
                } else {
                    transaction.setLeftBalance(account.getUsdAmount().subtract(request.getAmount()));
                }
                break;
        }

        return transaction;
    }


    private Balance check(Boolean isCurrencyAllowedForAccount, Currencies currencies) {
        switch (currencies) {
            case EUR:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.EUR, new BigDecimal(0));
                } else {
                    break;
                }
            case SEK:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.SEK, new BigDecimal(0));
                } else {
                    break;
                }
            case GBP:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.GBP, new BigDecimal(0));
                } else {
                    break;
                }
            case USD:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.USD, new BigDecimal(0));
                } else {
                    break;
                }
        }
        return null;
    }
}