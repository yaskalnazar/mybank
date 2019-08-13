package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.exceptions.WrongAccountTypeException;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Realization of interface {@link Mapper} for entity {@link CreditAccount}.
 *
 * @author Nazar Yaskal
 * @see CreditAccount
 * @see Mapper
 */
public class JDBCCreditMapper implements Mapper<CreditAccount> {

    @Override
    public CreditAccount mapToObject(ResultSet resultSet) throws SQLException {
        return CreditAccount.getBuilder()
                .setId(resultSet.getLong("account_id"))
                .setBalance(resultSet.getBigDecimal("balance").setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .setClosingDate(resultSet.getObject("closing_date", LocalDate.class))
                .setOwnerId(resultSet.getLong("owner_user_id"))
                .setAccountStatus(Account.AccountStatus.valueOf(resultSet.getString("account_status")))
                .setCreditLimit(resultSet.getBigDecimal("credit_limit").setScale(2, BigDecimal.ROUND_HALF_DOWN))
                .setCreditRate(resultSet.getBigDecimal("credit_rate"))
                .setAccruedInterest(resultSet.getBigDecimal("accrued_interest"))
                .build();
    }
}
