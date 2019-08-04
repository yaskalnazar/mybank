package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCCreditMapper implements Mapper<CreditAccount> {

    @Override
    public CreditAccount mapToObject(ResultSet resultSet) throws SQLException {
        return CreditAccount.getBuilder()
                .setId(resultSet.getLong("account_id"))
                .setBalance(resultSet.getBigDecimal("balance"))
                .setClosingDate(resultSet.getDate("closing_date").toLocalDate())
                .setOwnerId(resultSet.getLong("owner_user_id"))
                .setAccountStatus(Account.AccountStatus.valueOf(resultSet.getString("account_status")))
                .setCreditLimit(resultSet.getBigDecimal("credit_limit"))
                .setCreditRate(resultSet.getBigDecimal("credit_rate"))
                .setAccruedInterest(resultSet.getBigDecimal("accrued_interest"))
                .build();
    }
}
