package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.*;

public class JDBCMapperFactory implements MapperFactory {
    @Override
    public Mapper<User> getUserMapper() {
        return new JDBCUserMapper();
    }

    @Override
    public Mapper<DepositAccount> getDepositMapper() {
        return new JDBCDepositMapper();
    }

    @Override
    public Mapper<CreditAccount> getCreditMapper() {
        return new JDBCCreditMapper();
    }

    @Override
    public Mapper<CreditRequest> getCreditRequestMapper() {
        return new JDBCCreditRequestMapper();
    }

    @Override
    public Mapper<Account> getAccountMapper() {
        return new JDBCAccountMapper();
    }

    @Override
    public Mapper<Transaction> getTransactionMapper() {
        return new JDBCTransactionMapper();
    }
}
