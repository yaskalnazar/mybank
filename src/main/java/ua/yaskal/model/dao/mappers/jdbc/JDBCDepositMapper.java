package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCDepositMapper implements Mapper<DepositAccount> {

    @Override
    public DepositAccount mapToObject(ResultSet resultSet) throws SQLException {
        return DepositAccount.getBuilder()
                .setId(resultSet.getLong("account_id"))
                .setBalance(resultSet.getBigDecimal("balance").setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .setClosingDate(resultSet.getDate("closing_date").toLocalDate())
                .setOwnerId(resultSet.getLong("owner_user_id"))
                .setAccountStatus(Account.AccountStatus.valueOf(resultSet.getString("account_status")))
                .setDepositAmount(resultSet.getBigDecimal("deposit_amount").setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .setDepositRate(resultSet.getBigDecimal("deposit_rate"))
                .setDepositEndDate(resultSet.getDate("deposit_end_date").toLocalDate())
                .build();
    }
}
