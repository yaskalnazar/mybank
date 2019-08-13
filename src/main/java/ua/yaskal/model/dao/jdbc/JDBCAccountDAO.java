package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.AccountDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.exceptions.WrongAccountTypeException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchAccountException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCAccountDAO implements AccountDAO {
    private final static Logger logger = Logger.getLogger(JDBCAccountDAO.class);
    private DataSource dataSource;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;

    public JDBCAccountDAO(DataSource dataSource, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.dataSource = dataSource;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public Account getById(long id) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sqlRequestsBundle.getString("account.select.by.id"))) {
            statement.setLong(1, id);

            logger.debug("Select account " + statement);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getAccountMapper().mapToObject(resultSet);
            } else {
                logger.debug("No account with id:" + id);
                throw new NoSuchAccountException();
            }
        } catch (SQLException e) {
            logger.error("Can not get account", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                sqlRequestsBundle.getString("account.select.all"))) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                try {
                    accounts.add(mapperFactory.getAccountMapper().mapToObject(resultSet));
                } catch (WrongAccountTypeException e){
                    logger.warn("Can not map account id:" + e.getAccountId());
                }
            }
        } catch (SQLException e) {
            logger.error("Can not get all accounts: ", e);
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public void update(Account item) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                sqlRequestsBundle.getString("account.update.by.id"))) {
            statement.setString(1, item.getAccountType().name());
            statement.setBigDecimal(2, item.getBalance());
            statement.setObject(3, item.getClosingDate());
            statement.setLong(4, item.getOwnerId());
            statement.setString(5, item.getAccountStatus().name());
            statement.setLong(9, item.getId());

            logger.debug("Trying update account "+statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Account was not updated: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long addNew(Account item) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                sqlRequestsBundle.getString("account.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getAccountType().name());
            statement.setBigDecimal(2, item.getBalance());
            statement.setObject(3, item.getClosingDate());
            statement.setLong(4, item.getOwnerId());
            statement.setString(5, item.getAccountStatus().name());


            logger.debug("Add new account " + statement);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Account was not added: ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Account> getAllByOwnerId(long ownerId) {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                sqlRequestsBundle.getString("account.select.all.by.owner.id"))) {
            statement.setLong(1, ownerId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                try {
                    accounts.add(mapperFactory.getAccountMapper().mapToObject(resultSet));
                } catch (WrongAccountTypeException e){
                    logger.warn("Can not map account id:" + e.getAccountId());
                }
            }
        } catch (SQLException e) {
            logger.error("Can not get all user accounts", e);
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public List<Account> getAllByOwnerIdAndStatus(long ownerId, Account.AccountStatus status) {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                sqlRequestsBundle.getString("account.select.all.by.owner.id.and.status"))) {
            statement.setLong(1, ownerId);
            statement.setString(2, status.name());


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                try {
                    accounts.add(mapperFactory.getAccountMapper().mapToObject(resultSet));
                } catch (WrongAccountTypeException e){
                    logger.warn("Can not map account id:" + e.getAccountId());
                }
            }
        } catch (SQLException e) {
            logger.error("Can not get all user accounts", e);
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public List<Account> getAllByStatus(Account.AccountStatus status) {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                sqlRequestsBundle.getString("account.select.all.by.status"))) {
            statement.setString(1, status.name());


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                try {
                    accounts.add(mapperFactory.getAccountMapper().mapToObject(resultSet));
                } catch (WrongAccountTypeException e){
                    logger.warn("Can not map account id:" + e.getAccountId());
                }
            }
        } catch (SQLException e) {
            logger.error("Can not get all accounts", e);
            throw new RuntimeException(e);
        }
        return accounts;
    }

    @Override
    public void updateAccountStatus(long id, Account.AccountStatus status) {
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(
                sqlRequestsBundle.getString("account.update.status.by.id"))) {
            statement.setString(1, status.name());
            statement.setLong(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not update account status "+id+status, e);
            throw new RuntimeException(e);
        }
    }
}
