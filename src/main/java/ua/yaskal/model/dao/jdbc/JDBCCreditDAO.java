package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.CreditDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.DepositAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCCreditDAO implements CreditDAO {
    private final static Logger logger = Logger.getLogger(JDBCCreditDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;


    public JDBCCreditDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public CreditAccount getById(long id) {
        return null;
    }

    @Override
    public List<CreditAccount> getAll() {
        List<CreditAccount> creditAccounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.select.all"))) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditAccounts.add(mapperFactory.getCreditMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all credits: " + e);
            throw new RuntimeException(e);
        }
        return creditAccounts;
    }

    @Override
    public void update(CreditAccount item) {

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(CreditAccount item) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.insert.new"), Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, item.getAccountType().name());
            statement.setString(2, item.getBalance().toString());
            statement.setString(3, item.getClosingDate().toString());
            statement.setString(4, item.getOwnerId()+"");
            statement.setString(5, item.getAccountStatus().name());
            statement.setString(6, item.getCreditLimit().toString());
            statement.setString(7, item.getCreditRate().toString());
            statement.setString(8, item.getCreditLimit().toString());


            logger.debug("Add new Credit Account "+ statement);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Credit Account was not added: " + e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CreditAccount> getAllByOwnerId(long ownerId) {
        List<CreditAccount> creditAccounts = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.select.all.by.owner.id"))) {
            statement.setString(1, ownerId+"");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditAccounts.add(mapperFactory.getCreditMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all users credits: " + e);
            throw new RuntimeException(e);
        }
        return creditAccounts;
    }
}
