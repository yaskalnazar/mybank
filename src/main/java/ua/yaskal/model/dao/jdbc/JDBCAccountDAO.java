package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.AccountDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.exceptions.WrongAccountTypeException;
import ua.yaskal.model.exceptions.no.such.NoSuchAccountException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCAccountDAO implements AccountDAO {
    private final static Logger logger = Logger.getLogger(JDBCAccountDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;

    public JDBCAccountDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public Account getById(long id) {
        try (PreparedStatement statement = connection.prepareStatement(sqlRequestsBundle.getString("account.select.by.id"))) {
            statement.setString(1, id + "");

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
        return null;
    }

    @Override
    public void update(Account item) {

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(Account item) {
        return null;
    }

    @Override
    public List<Account> getAllByOwnerId(long ownerId) {
        List<Account> accounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("account.select.all.by.owner.id"))) {
            statement.setString(1, ownerId + "");

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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("account.select.all.by.owner.id.and.status"))) {
            statement.setString(1, ownerId + "");
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("account.select.all.by.status"))) {
            statement.setString(1, status.name());


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                try {
                    accounts.add(mapperFactory.getAccountMapper().mapToObject(resultSet));
                } catch (WrongAccountTypeException e){
                    logger.warn("Can not map account id:" + e.getAccountId());
                    continue;
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("account.update.status.by.id"))) {
            statement.setString(1, status.name());
            statement.setString(2, id+"");

            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not update account status "+id+status, e);
            throw new RuntimeException(e);
        }
    }
}
