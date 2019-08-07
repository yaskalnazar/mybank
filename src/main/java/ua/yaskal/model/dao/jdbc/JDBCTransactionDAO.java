package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.TransactionDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.NotEnoughMoneyException;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCTransactionDAO implements TransactionDAO {
    private final static Logger logger = Logger.getLogger(JDBCTransactionDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;
    private final static String ACCOUNTS_TRIGGER_SQLSTATE="12001";

    public JDBCTransactionDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public Transaction getById(long id) {
        return null;
    }

    @Override
    public List<Transaction> getAll() {
        return null;
    }

    @Override
    public void update(Transaction item) {

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(Transaction item) {
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement reduceBalance = connection.prepareStatement(
                sqlRequestsBundle.getString("account.reduce.balance"));
             PreparedStatement increaseBalance = connection.prepareStatement(
                     sqlRequestsBundle.getString("account.increase.balance"));
             PreparedStatement insertTransaction = connection.prepareStatement(
                     sqlRequestsBundle.getString("transaction.insert.new"), Statement.RETURN_GENERATED_KEYS)) {

            reduceBalance.setString(1, item.getTransactionAmount().toString());
            reduceBalance.setString(2, item.getSenderAccountId() + "");

            increaseBalance.setString(1, item.getTransactionAmount().toString());
            increaseBalance.setString(2, item.getReceiverAccountId() + "");


            insertTransaction.setString(1, item.getDate().toString());
            insertTransaction.setString(2, item.getTransactionAmount().toString());
            insertTransaction.setString(3, item.getReceiverAccountId() + "");
            insertTransaction.setString(4, item.getSenderAccountId() + "");


            logger.debug("Try add new Transaction" + insertTransaction);
            reduceBalance.execute();
            increaseBalance.execute();
            insertTransaction.execute();

            connection.commit();

            ResultSet resultSet = insertTransaction.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals(ACCOUNTS_TRIGGER_SQLSTATE)) {
                logger.warn("NotEnoughMoneyException");
                throw new NotEnoughMoneyException();
            }
            logger.error("Transaction was not added", e);
            throw new RuntimeException(e);
        }
    }
}
