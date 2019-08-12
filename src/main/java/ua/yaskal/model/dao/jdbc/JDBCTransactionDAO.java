package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.TransactionDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.NotEnoughMoneyException;
import ua.yaskal.model.exceptions.no.such.NoSuchActiveAccountException;
import ua.yaskal.model.exceptions.no.such.NoSuchPaymentException;
import ua.yaskal.model.exceptions.no.such.NoSuchTransactionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class JDBCTransactionDAO implements TransactionDAO {
    private final static Logger logger = Logger.getLogger(JDBCTransactionDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;
    private final static String ACCOUNTS_TRIGGER_SQLSTATE = "12001";

    public JDBCTransactionDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public Transaction getById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("transaction.select.by.id"))) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getTransactionMapper().mapToObject(resultSet);
            } else {
                logger.warn("No transaction with id:" + id);
                throw new NoSuchTransactionException();
            }
        } catch (SQLException e) {
            logger.error("Can not get transaction", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAll() {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("transaction.select.all"))) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapperFactory.getTransactionMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all transactions", e);
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public void update(Transaction item) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long addNew(Transaction item) {
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);

            try (PreparedStatement getActiveAccount = connection.prepareStatement(
                    sqlRequestsBundle.getString("account.select.active.by.id"), Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement reduceBalance = connection.prepareStatement(
                         sqlRequestsBundle.getString("account.reduce.balance"));
                 PreparedStatement increaseBalance = connection.prepareStatement(
                         sqlRequestsBundle.getString("account.increase.balance"));
                 PreparedStatement insertTransaction = connection.prepareStatement(
                         sqlRequestsBundle.getString("transaction.insert.new"), Statement.RETURN_GENERATED_KEYS)) {


                getActiveAccount.setLong(1, item.getReceiverAccountId());
                logger.debug("Select receiver account " + getActiveAccount);
                ResultSet resultSet = getActiveAccount.executeQuery();
                if (!resultSet.next()) {
                    logger.debug("No active account with id:" + item.getReceiverAccountId());
                    throw new NoSuchActiveAccountException();
                }

                reduceBalance.setBigDecimal(1, item.getTransactionAmount());
                reduceBalance.setLong(2, item.getSenderAccountId());

                increaseBalance.setBigDecimal(1, item.getTransactionAmount());
                increaseBalance.setLong(2, item.getReceiverAccountId());


                insertTransaction.setObject(1, item.getDate());
                insertTransaction.setBigDecimal(2, item.getTransactionAmount());
                insertTransaction.setLong(3, item.getReceiverAccountId());
                insertTransaction.setLong(4, item.getSenderAccountId());


                logger.debug("Try reduce balance" + reduceBalance);
                logger.debug("Try increase balance" + increaseBalance);
                logger.debug("Try add new Transaction" + insertTransaction);
                reduceBalance.execute();
                increaseBalance.execute();
                insertTransaction.execute();

                connection.commit();

                resultSet = insertTransaction.getGeneratedKeys();

                if (resultSet.next()) {
                    return resultSet.getLong(1);
                } else {
                    throw new SQLException();
                }
            } catch (SQLException e) {
                connection.rollback();
                if (e.getSQLState().equals(ACCOUNTS_TRIGGER_SQLSTATE)) {
                    logger.warn("NotEnoughMoneyException", e);
                    throw new NotEnoughMoneyException();
                }
                if (e.getErrorCode() == 1452) {
                    logger.warn("NoSuchActiveAccountException", e);
                    throw new NoSuchActiveAccountException();
                }
                logger.error("Transaction was not added", e);
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Transaction> getAllByReceiverId(long id) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("transaction.select.all.by.receiver.id"))) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapperFactory.getTransactionMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all receiver transactions", e);
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllBySenderId(long id) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("transaction.select.all.by.sender.id"))) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapperFactory.getTransactionMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all sender transactions", e);
            throw new RuntimeException(e);
        }
        return transactions;
    }

    @Override
    public List<Transaction> getAllByAccountId(long id) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("transaction.select.all.by.account.id"))) {
            statement.setLong(1, id);
            statement.setLong(2, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                transactions.add(mapperFactory.getTransactionMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all account transactions", e);
            throw new RuntimeException(e);
        }
        return transactions;
    }
}
