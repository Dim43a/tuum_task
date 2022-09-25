package com.example.tuum_task.mapper;

import com.example.tuum_task.model.transaction.Transaction;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface TransactionMapper {

    @Insert("INSERT INTO transactions (" +
            "account_id, amount, currency, direction, description, left_balance, date)" +
            "VALUES (" +
            "#{accountId}, #{amount}, #{currency}, #{direction}, #{description}, #{leftBalance}, #{date})")
    void insetTransaction(Transaction transaction);

    @Select("SELECT * FROM transactions WHERE account_id = #{accountId}")
    List<Transaction> getTransactionList(Long accountId);
}