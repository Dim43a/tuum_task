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

        try {
            accountMapper.insertAccount(account);
            return createAccountResponse(account);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is existing");
        }
    }

    @Override
    public AccountResponse getAccount(Long accountId) {
        try {
            Account account = accountMapper.selectAccountByAccountId(accountId);
            return createAccountResponse(account);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account not found");
        }
    }

    @Override
    public Transaction transaction(TransactionRequest request){

        if(request.getDescription().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Description missing");
        }
        Account account = accountMapper.selectAccountByAccountId(request.getAccountId());


        /*In case of option with multiple account currencies implementation with switch case
        is probably the most optimal solution.
        */
            switch (request.getCurrency()) {
                case "EUR" -> {
                    if (account.getEur()) {

                        if (Objects.equals(request.getDirection(), "IN")) {
                            incomeCheck(request, account);
                            accountMapper.updateEur(request.getAccountId(), account.getEurAmount().add(request.getAmount()));
                        } else if (Objects.equals(request.getDirection(), "OUT")) {
                            outcomeCheck(request, account);
                            accountMapper.updateEur(request.getAccountId(), account.getEurAmount().subtract(request.getAmount()));
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong direction, available IN and OUT");
                        }

                        Transaction eurTransaction = prepareTransaction(account, request, Currencies.EUR, request.getDirection());
                        transactionMapper.insetTransaction(eurTransaction);

                        List<Transaction> list = transactionListSorting(request);
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
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency or currency is not available for this account");
                    }
                }
                case "GPB" -> {
                    if (account.getGpb()) {
                        if (Objects.equals(request.getDirection(), "IN")) {
                            incomeCheck(request, account);
                            accountMapper.updateGbp(request.getAccountId(), account.getGbpAmount().add(request.getAmount()));
                        } else if (Objects.equals(request.getDirection(), "OUT")) {
                            outcomeCheck(request, account);
                            accountMapper.updateGbp(request.getAccountId(), account.getGbpAmount().subtract(request.getAmount()));
                        }

                        Transaction gbpTransaction = prepareTransaction(account, request, Currencies.GBP, request.getDirection());
                        transactionMapper.insetTransaction(gbpTransaction);

                        List<Transaction> list = transactionListSorting(request);
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
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency or currency is not available for this account");

                    }
                }
                case "SEK" -> {
                    if (account.getSek()) {
                        if (Objects.equals(request.getDirection(), "IN")) {
                            incomeCheck(request, account);
                            accountMapper.updateSek(request.getAccountId(), account.getSekAmount().add(request.getAmount()));
                        } else if (Objects.equals(request.getDirection(), "OUT")) {
                            outcomeCheck(request, account);
                            accountMapper.updateSek(request.getAccountId(), account.getSekAmount().subtract(request.getAmount()));
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong direction, available IN and OUT");
                        }

                        Transaction sekTransaction = prepareTransaction(account, request, Currencies.SEK, request.getDirection());
                        transactionMapper.insetTransaction(sekTransaction);

                        List<Transaction> list = transactionListSorting(request);
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
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency or currency is not available for this account");
                    }
                }
                case "USD" -> {
                    if (account.getUsd()) {
                        if (Objects.equals(request.getDirection(), "IN")) {
                            incomeCheck(request, account);
                            accountMapper.updateUsd(request.getAccountId(), account.getUsdAmount().add(request.getAmount()));
                        } else if (Objects.equals(request.getDirection(), "OUT")) {
                            outcomeCheck(request, account);
                            accountMapper.updateUsd(request.getAccountId(), account.getUsdAmount().subtract(request.getAmount()));
                        } else {
                            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid direction, available IN and OUT");
                        }

                        Transaction usdTransaction = prepareTransaction(account, request, Currencies.USD, request.getDirection());
                        transactionMapper.insetTransaction(usdTransaction);

                        List<Transaction> list = transactionListSorting(request);
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
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency or currency is not available for this account");
                    }
                }
                default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong currency inserted");
            }
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account is missing" + e.getMessage());
//        }
    }

    @Override
    public List<Transaction> getTransactionList(Long accountId) {

        try {
            return transactionMapper.getTransactionList(accountId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid account", e);
        }
    }

    private Account prepareAccount(AccountRequest request) {
        Account account = new Account();
        account.setCustomerId(request.getCustomerId());
        account.setCountry(request.getCountry());
        account.setEur(replaceNullWithFalse(request.getCurrencies().get("eur")));
        account.setSek(replaceNullWithFalse(request.getCurrencies().get("sek")));
        account.setGpb(replaceNullWithFalse(request.getCurrencies().get("gbp")));
        account.setUsd(replaceNullWithFalse(request.getCurrencies().get("usd")));

        return account;
    }

    private Boolean replaceNullWithFalse(Boolean currency) {
        return currency != null;
    }

    private List<Transaction> transactionListSorting(TransactionRequest request) {
        List<Transaction> list = transactionMapper.getTransactionList(request.getAccountId());
        //creating comparator for proper date sorting
        Comparator<Transaction> comparator = (tran1, tran2) -> tran2.getDate().compareTo(tran1.getDate());
        list.sort(comparator);

        return list;
    }

    private AccountResponse createAccountResponse(Account account) {
        ArrayList<Balance> balanceList = new ArrayList<>();
        Balance eurBalance = createBalance(account.getEur(), Currencies.EUR);
        Balance sekBalance = createBalance(account.getSek(), Currencies.SEK);
        Balance gbpBalance = createBalance(account.getGpb(), Currencies.GBP);
        Balance usdBalance = createBalance(account.getUsd(), Currencies.USD);

        balanceList.add(eurBalance);
        balanceList.add(sekBalance);
        balanceList.add(gbpBalance);
        balanceList.add(usdBalance);

        //Remove null elements from arrayList
        balanceList.removeAll(Collections.singletonList(null));

        if(balanceList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency");
        }
        Account accountId = accountMapper.selectAccountByCustomerId(account.getCustomerId());

        return new AccountResponse(
                accountId.getAccountId(),
                account.getCustomerId(),
                account.getCountry(),
                balanceList
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

    private void incomeCheck(TransactionRequest request, Account account) {
        if(request.getAmount().doubleValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid amount");
        }
    }

    private void outcomeCheck(TransactionRequest request, Account account) {
        if (account.getEurAmount().subtract(request.getAmount()).doubleValue() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not enough money on the account to complete transaction");
        }
    }

    private Balance createBalance(Boolean isCurrencyAllowedForAccount, Currencies currencies) {
        switch (currencies) {
            case EUR:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.EUR, new BigDecimal("0.0"));
                } else {
                    break;
                }
            case SEK:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.SEK, new BigDecimal("0.0"));
                } else {
                    break;
                }
            case GBP:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.GBP, new BigDecimal("0.0"));
                } else {
                    break;
                }
            case USD:
                if(isCurrencyAllowedForAccount) {
                    return new Balance(Currencies.USD, new BigDecimal("0.0"));
                } else {
                    break;
                }
        }
        return null;
    }
}