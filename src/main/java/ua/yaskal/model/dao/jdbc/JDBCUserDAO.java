package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.UserDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.NonUniqueEmailException;
import ua.yaskal.model.exceptions.no.such.NoSuchUserException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCUserDAO implements UserDAO {
    private final static Logger logger = Logger.getLogger(JDBCUserDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;

    public JDBCUserDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public User getById(long id) {
        try (PreparedStatement getUserStatement = connection.prepareStatement(sqlRequestsBundle.getString("user.select.by.id"))) {
            getUserStatement.setString(1, id + "");

            logger.debug("Select user " + getUserStatement);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getUserMapper().mapToObject(resultSet);
            } else {
                logger.debug("No user with id:" + id);
                throw new NoSuchUserException();
            }
        } catch (SQLException e) {
            logger.error("Can not get user", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection
                .prepareStatement(sqlRequestsBundle.getString("user.select.all"))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(mapperFactory.getUserMapper().mapToObject(resultSet));
            }
            return users;
        } catch (SQLException e) {
            logger.error("Can not get all users", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User item) {

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(User item) throws NonUniqueEmailException {
        try (PreparedStatement addUserStatement = connection.prepareStatement(sqlRequestsBundle.getString("user.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            addUserStatement.setString(1, item.getEmail());
            addUserStatement.setString(2, item.getName());
            addUserStatement.setString(3, item.getSurname());
            addUserStatement.setString(4, item.getPatronymic());
            addUserStatement.setString(5, item.getRole().getStringRole());
            addUserStatement.setString(6, item.getPassword());

            logger.debug("Add new user " + addUserStatement);
            addUserStatement.execute();

            ResultSet resultSet = addUserStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("User was not added", e);
            if (e.getErrorCode() == 1062) {
                throw new NonUniqueEmailException();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (PreparedStatement getUserStatement = connection.prepareStatement(sqlRequestsBundle.getString("user.select.by.email"))) {
            getUserStatement.setString(1, email);

            logger.debug("Select user " + getUserStatement);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getUserMapper().mapToObject(resultSet);
            } else {
                logger.debug("No user with email:" + email);
                throw new NoSuchUserException();
            }
        } catch (SQLException e) {
            logger.error("Can not get user", e);
            throw new RuntimeException(e);
        }
    }
}
