package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.entity.Payment;
import ua.yaskal.model.entity.Transaction;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Realization of interface {@link Mapper} for entity {@link Transaction}.
 *
 * @author Nazar Yaskal
 * @see CreditRequest
 * @see Transaction
 */
public class JDBCTransactionMapper implements Mapper<Transaction> {
    @Override
    public Transaction mapToObject(ResultSet resultSet) throws SQLException {
        return Transaction.getBuilder()
                .setId(resultSet.getLong("transaction_id"))
                .setDate(resultSet.getObject("date", LocalDateTime.class))
                .setTransactionAmount(resultSet.getBigDecimal("transaction_amount").setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .setReceiverAccount(resultSet.getLong("receiver_account_account_id"))
                .setSenderAccount(resultSet.getLong("sender_account_account_id"))
                .build();
    }
}
