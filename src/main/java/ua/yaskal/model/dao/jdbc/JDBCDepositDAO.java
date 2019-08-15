package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.CreditDAO;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.dao.TransactionDAO;
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
 * Realization of {@link DepositDAO} using JDBC.
 *
 * @author Nazar Yaskal
 * @see ua.yaskal.model.dao.DAO
 * @see DepositDAO
 * @see DepositAccount
 */
public class JDBCDepositDAO implements DepositDAO {
    private final static String ACCOUNTS_TRIGGER_SQLSTATE = "12001";
    private final static Logger logger = Logger.getLogger(JDBCDepositDAO.class);
    private DataSource dataSource;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;
    private TransactionDAO transactionDAO;


    public JDBCDepositDAO(DataSource dataSource, ResourceBundle sqlRequestsBundle,
                          MapperFactory mapperFactory, TransactionDAO transactionDAO) {
        this.dataSource = dataSource;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
        this.transactionDAO = transactionDAO;
    }

    @Override
    public DepositAccount getById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getDepositStatement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.select.by.id"))) {
            getDepositStatement.setLong(1, id);

            logger.debug("Select deposit " + getDepositStatement);
            ResultSet resultSet = getDepositStatement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getDepositMapper().mapToObject(resultSet);
            } else {
                logger.debug("No deposit with id:" + id);
                throw new NoSuchAccountException();
            }
        } catch (SQLException e) {
            logger.error("Can not get deposit", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DepositAccount> getAll() {
        List<DepositAccount> depositAccounts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.select.all"))) {


            logger.debug("Getting all deposits" + statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                depositAccounts.add(mapperFactory.getDepositMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all deposits ", e);
            throw new RuntimeException(e);
        }
        return depositAccounts;
    }

    @Override
    public void update(DepositAccount item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.update.by.id"))) {
            statement.setString(1, item.getAccountType().name());
            statement.setBigDecimal(2, item.getBalance());
            statement.setObject(3, item.getClosingDate());
            statement.setLong(4, item.getOwnerId());
            statement.setString(5, item.getAccountStatus().name());
            statement.setBigDecimal(6, item.getDepositAmount());
            statement.setBigDecimal(7, item.getDepositRate());
            statement.setObject(8, item.getDepositEndDate());
            statement.setLong(9, item.getId());

            logger.debug("Trying update deposit" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Deposit was not updated: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long addNew(DepositAccount item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getAccountType().name());
            statement.setBigDecimal(2, item.getBalance());
            statement.setObject(3, item.getClosingDate());
            statement.setLong(4, item.getOwnerId());
            statement.setString(5, item.getAccountStatus().name());
            statement.setBigDecimal(6, item.getDepositAmount());
            statement.setBigDecimal(7, item.getDepositRate());
            statement.setObject(8, item.getDepositEndDate());


            logger.debug("Add new Deposit Account " + statement);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Deposit Account was not added", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DepositAccount> getAllByOwnerId(long ownerId) {
        List<DepositAccount> depositAccounts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.select.all.by.owner.id"))) {
            statement.setLong(1, ownerId);

            logger.debug("Getting all user deposits" + statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                depositAccounts.add(mapperFactory.getDepositMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all users deposits", e);
            throw new RuntimeException(e);
        }
        return depositAccounts;
    }

    @Override
    public List<DepositAccount> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status) {
        List<DepositAccount> depositAccounts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.select.all.by.owner.id.and.status"))) {
            statement.setLong(1, ownerId);
            statement.setString(2, status.name());

            logger.debug("Getting all user deposits" + statement);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                depositAccounts.add(mapperFactory.getDepositMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all users deposits", e);
            throw new RuntimeException(e);
        }
        return depositAccounts;
    }

    @Override
    public void updateDepositAmount(long id, BigDecimal amount) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.update.amount.by.id"))) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, id);


            logger.debug("Updating deposit amount" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not update deposit amount", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDepositRate(long id, BigDecimal rate) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     sqlRequestsBundle.getString("deposit.update.rate.by.id"))) {
            statement.setBigDecimal(1, rate);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not update deposit rate", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This method used in pagination mechanism.
     *
     * @author Nazar Yaskal
     */
    @Override
    public PaginationDTO<DepositAccount> getAllPage(long itemsPerPage, long currentPage) {
        PaginationDTO<DepositAccount> paginationDTO = new PaginationDTO<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            try (PreparedStatement countDeposits = connection.prepareStatement(
                    sqlRequestsBundle.getString("deposit.count.all"));
                 PreparedStatement getDeposits = connection.prepareStatement(
                         sqlRequestsBundle.getString("deposit.get.page.all"))) {

                long depositsNumber;
                ResultSet resultSet = countDeposits.executeQuery();
                if (resultSet.next()) {
                    depositsNumber = resultSet.getInt("number");
                } else {
                    throw new SQLException();
                }

                long pagesNumber = depositsNumber % itemsPerPage == 0 ? depositsNumber / itemsPerPage : (depositsNumber / itemsPerPage) + 1;


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

                getDeposits.setLong(1, itemsPerPage);
                getDeposits.setLong(2, offset);

                logger.debug("Trying increase get deposits page " + getDeposits);
                resultSet = getDeposits.executeQuery();

                List<DepositAccount> depositAccounts = new ArrayList<>();
                while (resultSet.next()) {
                    depositAccounts.add(mapperFactory.getDepositMapper().mapToObject(resultSet));
                }

                paginationDTO.setPagesNumber(pagesNumber);
                paginationDTO.setCurrentPage(currentPage);
                paginationDTO.setItemsPerPage(itemsPerPage);
                paginationDTO.setItems(depositAccounts);

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

    /**
     * This method used for updating making new deposit contribution when the previous one is over.
     * Two other methods {@link #addNewTransaction(Transaction, Connection)}
     * and {@link #update(DepositAccount, Connection)} are used in the process
     *
     * @author Nazar Yaskal
     * @see ua.yaskal.controller.command.user.MakeNewContributionCommand
     * @see #addNewTransaction(Transaction, Connection)
     * @see #update(DepositAccount, Connection)
     */
    @Override
    public void newDepositContribution(NewDepositContributionDTO contributionDTO) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            connection.setAutoCommit(false);

            try (PreparedStatement getDepositStatement = connection.prepareStatement(
                    sqlRequestsBundle.getString("deposit.select.by.id"));
            ) {

                getDepositStatement.setLong(1, contributionDTO.getDepositId());


                logger.debug("Select deposit " + getDepositStatement);
                ResultSet resultSet = getDepositStatement.executeQuery();
                DepositAccount depositAccount;
                if (resultSet.next()) {
                    depositAccount = mapperFactory.getDepositMapper().mapToObject(resultSet);
                } else {
                    logger.debug("No deposit with id:" + contributionDTO.getDepositId());
                    throw new NoSuchAccountException();
                }

                if (depositAccount.getDepositEndDate().isAfter(LocalDate.now())) {
                    connection.rollback();
                    throw new DepositAlreadyActiveException();
                }

                addNewTransaction(contributionDTO.getTransaction(), connection);


                depositAccount.setDepositEndDate(LocalDate.now().plusMonths(contributionDTO.getMonthsAmount()));
                depositAccount.setDepositAmount(depositAccount.getDepositAmount().add(
                        contributionDTO.getDepositAmount()));
                depositAccount.setDepositRate(contributionDTO.getDepositRate());
                update(depositAccount, connection);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error("New deposit contribution was not updated", e);
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * This method used for money transfer to service account when making new deposit contribution
     *
     * @author Nazar Yaskal
     * @see ua.yaskal.controller.command.user.MakeNewContributionCommand
     * @see #newDepositContribution(NewDepositContributionDTO)
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

    /**
     * This method used for updating depositAmount, depositRate and depositEndDate
     * when making new deposit contribution
     *
     * @author Nazar Yaskal
     * @see ua.yaskal.controller.command.user.MakeNewContributionCommand
     * @see #newDepositContribution(NewDepositContributionDTO)
     */
    private void update(DepositAccount item, Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.update.by.id"))) {
            statement.setString(1, item.getAccountType().name());
            statement.setBigDecimal(2, item.getBalance());
            statement.setObject(3, item.getClosingDate());
            statement.setLong(4, item.getOwnerId());
            statement.setString(5, item.getAccountStatus().name());
            statement.setBigDecimal(6, item.getDepositAmount());
            statement.setBigDecimal(7, item.getDepositRate());
            statement.setObject(8, item.getDepositEndDate());
            statement.setLong(9, item.getId());

            logger.debug("Trying update deposit" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            logger.error("Deposit was not updated: ", e);
            throw new RuntimeException(e);
        }
    }
}
