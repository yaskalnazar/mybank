package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.exceptions.WrongAccountTypeException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCAccountMapper implements Mapper<Account> {
    @Override
    public Account mapToObject(ResultSet resultSet) throws SQLException {
        try {
            switch (Account.AccountType.valueOf(resultSet.getString("account_type"))){
                case CREDIT:
                    return new JDBCCreditMapper().mapToObject(resultSet);
                case DEPOSIT:
                    return new JDBCDepositMapper().mapToObject(resultSet);
                default:
                    throw new WrongAccountTypeException(resultSet.getLong("account_id"));
            }
        } catch (IllegalArgumentException e){
            throw new WrongAccountTypeException(resultSet.getLong("account_id"));
        }

    }
}
