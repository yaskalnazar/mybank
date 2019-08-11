package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.Payment;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class JDBCPaymentMapper implements Mapper<Payment> {
    @Override
    public Payment mapToObject(ResultSet resultSet) throws SQLException {
        return Payment.getBuilder()
                .setId(resultSet.getLong("payment_id"))
                .setAmount(resultSet.getBigDecimal("amount").setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .setDate(resultSet.getObject("date", LocalDateTime.class))
                .setPaymentStatus(Payment.PaymentStatus.valueOf(resultSet.getString("payment_status")))
                .setPayerAccountId(resultSet.getLong("payer_account_account_id"))
                .setRequesterAccountId(resultSet.getLong("requester_account_account_id"))
                .setMessage(Optional.of(resultSet.getString("payment_message")).orElse(""))
                .build();
    }
}
