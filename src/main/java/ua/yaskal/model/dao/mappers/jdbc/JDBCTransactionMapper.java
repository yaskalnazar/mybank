package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.Transaction;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCTransactionMapper implements Mapper<Transaction> {
    @Override
    public Transaction mapToObject(ResultSet resultSet) throws SQLException {
        return Transaction.getBuilder()
                .setId(resultSet.getLong("transaction_id"))
                .setDate(resultSet.getDate("date").toLocalDate())
                .setTransactionAmount(resultSet.getBigDecimal("transaction_amount").setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .setReceiverAccount(resultSet.getLong("receiver_account_account_id"))
                .setSenderAccount(resultSet.getLong("sender_account_account_id"))
                .build();
    }
}
