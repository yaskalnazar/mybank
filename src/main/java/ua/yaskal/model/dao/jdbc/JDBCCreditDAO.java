package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.AccountDAO;
import ua.yaskal.model.dao.CreditDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.dto.NewDepositContributionDTO;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.exceptions.message.key.DepositAlreadyActiveException;
import ua.yaskal.model.exceptions.message.key.NotEnoughMoneyException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchAccountException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchActiveAccountException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchPageException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Realization of {@link CreditDAO} using JDBC.
 *
 * @author Nazar Yaskal
 * @see ua.yaskal.model.dao.DAO
 * @see CreditDAO
 * @see CreditAccount
 */
public class JDBCCreditDAO implements CreditDAO {
    private final static String ACCOUNTS_TRIGGER_SQLSTATE = "12001";
    private final static Logger logger = Logger.getLogger(JDBCCreditDAO.class);
    private DataSource dataSource;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;


    public JDBCCreditDAO(DataSource dataSource, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.dataSource = dataSource;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public CreditAccount getById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getCreditStatement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.select.by.id"))) {
            getCreditStatement.setLong(1, id);

            logger.debug("Select credit " + getCreditStatement);
            ResultSet resultSet = getCreditStatement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getCreditMapper().mapToObject(resultSet);
            } else {
                logger.debug("No credit with id:" + id);
                throw new NoSuchAccountException();
            }
        } catch (SQLException e) {
            logger.error("Can not get credit", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CreditAccount> getAll() {
        List<CreditAccount> creditAccounts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.select.all"))) {


            logger.debug("Getting all credits" + statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditAccounts.add(mapperFactory.getCreditMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all credits", e);
            throw new RuntimeException(e);
        }
        return creditAccounts;
    }

    @Override
    public void update(CreditAccount item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.update.by.id"))) {
            statement.setString(1, item.getAccountType().name());
            statement.setBigDecimal(2, item.getBalance());
            statement.setObject(3, item.getClosingDate());
            statement.setLong(4, item.getOwnerId());
            statement.setString(5, item.getAccountStatus().name());
            statement.setBigDecimal(6, item.getCreditLimit());
            statement.setBigDecimal(7, item.getCreditRate());
            statement.setBigDecimal(8, item.getAccruedInterest());
            statement.setLong(9, item.getId());

            logger.debug("Trying update credit" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Credit Account was not updated: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long addNew(CreditAccount item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getAccountType().name());
            statement.setBigDecimal(2, item.getBalance());
            statement.setObject(3, item.getClosingDate());
            statement.setLong(4, item.getOwnerId());
            statement.setString(5, item.getAccountStatus().name());
            statement.setBigDecimal(6, item.getCreditLimit());
            statement.setBigDecimal(7, item.getCreditRate());
            statement.setBigDecimal(8, item.getAccruedInterest());


            logger.debug("Add new Credit Account " + statement);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Credit Account was not added: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CreditAccount> getAllByOwnerId(long ownerId) {
        List<CreditAccount> creditAccounts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.select.all.by.owner.id"))) {
            statement.setLong(1, ownerId);


            logger.debug("Getting all user credits" + statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditAccounts.add(mapperFactory.getCreditMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all user credits", e);
            throw new RuntimeException(e);
        }
        return creditAccounts;
    }

    @Override
    public List<CreditAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status) {
        List<CreditAccount> creditAccounts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.select.all.by.owner.id.and.status"))) {
            statement.setLong(1, ownerId);
            statement.setString(2, status.name());


            logger.debug("Getting all user credits" + statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditAccounts.add(mapperFactory.getCreditMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all user credits", e);
            throw new RuntimeException(e);
        }
        return creditAccounts;
    }

    @Override
    public void increaseAccruedInterestById(long id, BigDecimal accruedInterest) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.add.increase.interest.by.id"))) {
            statement.setBigDecimal(1, accruedInterest);
            statement.setLong(2, id);

            logger.debug("Trying increase accrued interest" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not increase credit accruedInterest", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void reduceAccruedInterestById(long id, BigDecimal accruedInterest) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("credit.add.reduce.interest.by.id"))) {
            statement.setBigDecimal(1, accruedInterest);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not reduce credit accruedInterest", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This method used in pagination mechanism.
     *
     * @author Nazar Yaskal
     */
    @Override
    public PaginationDTO<CreditAccount> getAllPage(long itemsPerPage, long currentPage) {
        PaginationDTO<CreditAccount> paginationDTO = new PaginationDTO<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            try (PreparedStatement countCredits = connection.prepareStatement(
                    sqlRequestsBundle.getString("credit.count.all"));
                 PreparedStatement getCredits = connection.prepareStatement(
                         sqlRequestsBundle.getString("credit.get.page.all"))) {

                long creditsNumber;
                ResultSet resultSet = countCredits.executeQuery();
                if (resultSet.next()) {
                    creditsNumber = resultSet.getInt("number");
                } else {
                    throw new SQLException();
                }

                long pagesNumber = creditsNumber % itemsPerPage == 0 ? creditsNumber / itemsPerPage : (creditsNumber / itemsPerPage) + 1;


                if (pagesNumber == 0) {
                    paginationDTO.setPagesNumber(1);
                    paginationDTO.setCurrentPage(1);
                    paginationDTO.setItemsPerPage(itemsPerPage);
                    paginationDTO.setItems(Collections.emptyList());

                    connection.commit();

                    return paginationDTO;
                }

                if (currentPage > pagesNumber) {
                    throw new NoSuchPageException();
                }

                long offset = (currentPage - 1) * itemsPerPage;

                getCredits.setLong(1, itemsPerPage);
                getCredits.setLong(2, offset);

                logger.debug("Trying increase get credits page " + getCredits);
                resultSet = getCredits.executeQuery();

                List<CreditAccount> creditAccounts = new ArrayList<>();
                while (resultSet.next()) {
                    creditAccounts.add(mapperFactory.getCreditMapper().mapToObject(resultSet));
                }

                paginationDTO.setPagesNumber(pagesNumber);
                paginationDTO.setCurrentPage(currentPage);
                paginationDTO.setItemsPerPage(itemsPerPage);
                paginationDTO.setItems(creditAccounts);

                connection.commit();

                return paginationDTO;

            } catch (SQLException e) {
                connection.rollback();

                logger.error(e);
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void payAccruedInterest(Transaction transaction, long receiverCreditId) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(
                         sqlRequestsBundle.getString("credit.add.reduce.interest.by.id"))) {
                statement.setBigDecimal(1, transaction.getTransactionAmount());
                statement.setLong(2, receiverCreditId);

                statement.executeUpdate();
                addNewTransaction(transaction,connection);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error("Can not reduce credit accruedInterest", e);
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This method used for money transfer to service account when paying accrued interest
     *
     * @author Nazar Yaskal
     * @see ua.yaskal.controller.command.user.MakeNewContributionCommand
     * @see #payAccruedInterest(Transaction, long)
     */
    private long addNewTransaction(Transaction item, Connection connection) throws SQLException {
        try (PreparedStatement getActiveAccount = connection.prepareStatement(
                sqlRequestsBundle.getString("account.select.active.by.id"), Statement.RETURN_GENERATED_KEYS);
             PreparedStatement getBalanceAndCreditLimit = connection.prepareStatement(
                     sqlRequestsBundle.getString("account.select.balance.and.credit.limit.by.id"), Statement.RETURN_GENERATED_KEYS);
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
                logger.debug("No active receiver account with id:" + item.getReceiverAccountId());
                throw new NoSuchActiveAccountException();
            }

            getBalanceAndCreditLimit.setLong(1, item.getSenderAccountId());
            logger.debug("Check balance " + getBalanceAndCreditLimit);
            resultSet = getBalanceAndCreditLimit.executeQuery();
            if (resultSet.next()) {
                BigDecimal balance = resultSet.getBigDecimal("balance");
                BigDecimal creditLimit = Optional.ofNullable(
                        resultSet.getBigDecimal("credit_limit")).orElse(BigDecimal.ZERO);
                if (balance.add(creditLimit).compareTo(item.getTransactionAmount()) < 0) {
                    logger.warn("Not enough money on account " + item.getSenderAccountId() + " for sending " + item.getTransactionAmount());
                    throw new NotEnoughMoneyException();
                }
            } else {
                logger.debug("No active sender account with id:" + item.getSenderAccountId());
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
    }
}
