package ua.yaskal.model.dao.jdbc;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import ua.yaskal.model.dao.*;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.dao.mappers.jdbc.JDBCMapperFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JDBCDaoFactory extends DAOFactory {
    private final static Logger logger = Logger.getLogger(JDBCDaoFactory.class);
    private static ResourceBundle databaseProperties = ResourceBundle.getBundle("database");
    private static ResourceBundle sqlRequestsBundle = ResourceBundle.getBundle("SQLRequests");
    private  MapperFactory mapperFactory = new JDBCMapperFactory();
    private DataSource dataSource = JDBCConnectionsPool.getDataSource();



    @Override
    public UserDAO createUserDAO() {
        return new JDBCUserDAO(getConnection(), sqlRequestsBundle, mapperFactory);
    }

    @Override
    public DepositDAO createDepositDAO() {
        return new JDBCDepositDAO(getConnection(), sqlRequestsBundle, mapperFactory, createTransactionDAO());
    }

    @Override
    public CreditDAO createCreditDAO() {
        return new JDBCCreditDAO(getConnection(), sqlRequestsBundle, mapperFactory);
    }

    @Override
    public CreditRequestDAO createCreditRequestDAO() {
        return new JDBCCreditRequestDAO(getConnection(), sqlRequestsBundle, mapperFactory);
    }

    @Override
    public TransactionDAO createTransactionDAO() {
        return new JDBCTransactionDAO(getConnection(), sqlRequestsBundle, mapperFactory);
    }

    @Override
    public AccountDAO createAccountDAO() {
        return new JDBCAccountDAO(dataSource, sqlRequestsBundle, mapperFactory);
    }

    @Override
    public PaymentDAO createPaymentDAO() {
        return new JDBCPaymentDAO(getConnection(), sqlRequestsBundle, mapperFactory);
    }

    private Connection getConnection() {
        try {
            logger.debug("Getting connection to DB URL:{"
                    + databaseProperties.getString("db.connection.datasource.url")
                    + "} Password:{"
                    + databaseProperties.getString("db.connection.password")
                    + "} User:{"
                    + databaseProperties.getString("db.connection.user") + "}");

            Class.forName(databaseProperties.getString("db.connection.driver"));
            return DriverManager.getConnection(
                    databaseProperties.getString("db.connection.datasource.url"),
                    databaseProperties.getString("db.connection.password"),
                    databaseProperties.getString("db.connection.user"));
        } catch (SQLException e) {
            logger.error("Cannot get connection to db", e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.error("Not found class for sql driver", e);
            throw new RuntimeException(e);
        }
    }




}
