package ua.yaskal.model.dao.jdbc;


import ua.yaskal.model.dao.*;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.dao.mappers.jdbc.JDBCMapperFactory;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class JDBCDaoFactory extends DAOFactory {
    private static ResourceBundle sqlRequestsBundle = ResourceBundle.getBundle("SQLRequests");
    private MapperFactory mapperFactory = new JDBCMapperFactory();
    private DataSource dataSource = JDBCConnectionsPool.getDataSource();


    @Override
    public UserDAO createUserDAO() {
        return new JDBCUserDAO(dataSource, sqlRequestsBundle, mapperFactory);
    }

    @Override
    public DepositDAO createDepositDAO() {
        return new JDBCDepositDAO(dataSource, sqlRequestsBundle, mapperFactory, createTransactionDAO());
    }

    @Override
    public CreditDAO createCreditDAO() {
        return new JDBCCreditDAO(dataSource, sqlRequestsBundle, mapperFactory);
    }

    @Override
    public CreditRequestDAO createCreditRequestDAO() {
        return new JDBCCreditRequestDAO(dataSource, sqlRequestsBundle, mapperFactory);
    }

    @Override
    public TransactionDAO createTransactionDAO() {
        return new JDBCTransactionDAO(dataSource, sqlRequestsBundle, mapperFactory);
    }

    @Override
    public AccountDAO createAccountDAO() {
        return new JDBCAccountDAO(dataSource, sqlRequestsBundle, mapperFactory);
    }

    @Override
    public PaymentDAO createPaymentDAO() {
        return new JDBCPaymentDAO(dataSource, sqlRequestsBundle, mapperFactory);
    }




}
