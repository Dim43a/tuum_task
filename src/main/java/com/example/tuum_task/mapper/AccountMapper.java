package com.example.tuum_task.mapper;

import com.example.tuum_task.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM ACCOUNT")
    List<Account> findAll();

    @Insert("INSERT INTO account (" +
            "customer_id, " +
            "country," +
            "eur," +
            "sek," +
            "gpb," +
            "usd)" +
            "VALUES (#{customerId}, #{country}, #{eur}, #{sek}, #{gpb}, #{usd})")
    void insertAccount(Account account);


    @Select("SELECT * FROM account WHERE customer_id = #{customerId}")
    Account selectAccount(Long customerId);
}