package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.User;

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
}
