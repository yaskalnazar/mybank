package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.DepositAccount;

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
        return null;
    }

    @Override
    public List<DepositAccount> getAll() {
        List<DepositAccount> depositAccounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.select.all"))) {

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

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(DepositAccount item) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getAccountType().name());
            statement.setString(2, item.getBalance().toString());
            statement.setString(3, item.getClosingDate().toString());
            statement.setString(4, item.getOwnerId() + "");
            statement.setString(5, item.getAccountStatus().name());
            statement.setString(6, item.getDepositAmount().toString());
            statement.setString(7, item.getDepositRate().toString());
            statement.setString(8, item.getDepositEndDate().toString());


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
            statement.setString(1, ownerId + "");

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
            statement.setString(1, ownerId + "");
            statement.setString(2, status.name());


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
}
