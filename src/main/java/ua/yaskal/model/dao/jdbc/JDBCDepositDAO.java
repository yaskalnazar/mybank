package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchAccountException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCDepositDAO implements DepositDAO {
    private final static Logger logger = Logger.getLogger(JDBCDepositDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;


    public JDBCDepositDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public DepositAccount getById(long id) {
        try (PreparedStatement getUserStatement = connection.prepareStatement(sqlRequestsBundle.getString("deposit.select.by.id"))) {
            getUserStatement.setLong(1, id);

            logger.debug("Select deposit " + getUserStatement);
            ResultSet resultSet = getUserStatement.executeQuery();
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.select.all"))) {


            logger.debug("Getting all deposits"+statement);
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

            logger.debug("Trying update deposit"+statement);
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
        try (PreparedStatement statement = connection.prepareStatement(
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.select.all.by.owner.id"))) {
            statement.setLong(1, ownerId);

            logger.debug("Getting all user deposits"+statement);
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.select.all.by.owner.id.and.status"))) {
            statement.setLong(1, ownerId);
            statement.setString(2, status.name());

            logger.debug("Getting all user deposits"+statement);
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.update.amount.by.id"))) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, id);


            logger.debug("Updating deposit amount"+statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not update deposit amount", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateDepositRate(long id, BigDecimal rate) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.update.rate.by.id"))) {
            statement.setBigDecimal(1, rate);
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not update deposit rate", e);
            throw new RuntimeException(e);
        }
    }
}
