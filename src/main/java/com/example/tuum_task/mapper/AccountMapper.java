package com.example.tuum_task.mapper;

import com.example.tuum_task.model.account.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.math.BigDecimal;

@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO account (" +
            "customer_id, " +
            "country," +
            "eur," +
            "sek," +
            "gpb," +
            "usd)" +
            "VALUES (#{customerId}, #{country}, #{eur}, #{sek}, #{gpb}, #{usd})")
    void insertAccount(Account account);

    @Update("UPDATE account SET eur_amount = #{amount} WHERE account_id = #{accountId}")
    void updateEur(Long accountId, BigDecimal amount);

    @Update("UPDATE account SET gbp_amount = #{amount} WHERE account_id = #{accountId}")
    void updateGbp(Long accountId, BigDecimal amount);

    @Update("UPDATE account SET sek_amount = #{amount} WHERE account_id = #{accountId}")
    void updateSek(Long accountId, BigDecimal amount);

    @Update("UPDATE account SET usd_amount = #{amount} WHERE account_id = #{accountId}")
    void updateUsd(Long accountId, BigDecimal amount);


    @Select("SELECT * FROM account WHERE customer_id = #{customerId}")
    Account selectAccountByCustomerId(Long customerId);

    @Select("SELECT * FROM account WHERE account_id = #{accountId}")
    Account selectAccountByAccountId(Long accountId);

}