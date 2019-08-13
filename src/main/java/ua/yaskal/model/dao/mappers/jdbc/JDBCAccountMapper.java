package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.exceptions.WrongAccountTypeException;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Realization of interface {@link Mapper} for abstract entity {@link Account}. It is used as master-mapper for all
 * Account entities. It delegates its responsibilities to sub-mappers. If account has another then CREDIT or DEPOSIT
 * type then throwing {@link WrongAccountTypeException}
 *
 * @author Nazar Yaskal
 * @see Account
 * @see Mapper
 * @see JDBCCreditMapper
 * @see JDBCDepositMapper
 */
public class JDBCAccountMapper implements Mapper<Account> {
    @Override
    public Account mapToObject(ResultSet resultSet) throws SQLException {
        try {
            switch (Account.AccountType.valueOf(resultSet.getString("account_type"))) {
                case CREDIT:
                    return new JDBCCreditMapper().mapToObject(resultSet);
                case DEPOSIT:
                    return new JDBCDepositMapper().mapToObject(resultSet);
                default:
                    throw new WrongAccountTypeException(resultSet.getLong("account_id"));
            }
        } catch (IllegalArgumentException e) {
            throw new WrongAccountTypeException(resultSet.getLong("account_id"));
        }

    }
}
