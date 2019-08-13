package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.dao.TransactionDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.dto.NewDepositContributionDTO;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.exceptions.message.key.DepositAlreadyActiveException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchAccountException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchPageException;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCDepositDAO implements DepositDAO {
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

                try {
                    transactionDAO.addNew(contributionDTO.getTransaction());
                } catch (RuntimeException e) {
                    connection.rollback();
                    throw new RuntimeException(e);
                }


                depositAccount.setDepositEndDate(LocalDate.now().plusMonths(contributionDTO.getMonthsAmount()));
                depositAccount.setDepositAmount(depositAccount.getDepositAmount().add(
                        contributionDTO.getDepositAmount()));
                depositAccount.setDepositRate(contributionDTO.getDepositRate());

                update(depositAccount);
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
}
