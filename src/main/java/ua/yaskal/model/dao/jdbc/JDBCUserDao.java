package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.UserDao;
import ua.yaskal.model.dao.jdbc.sql.requests.UserRequests;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.dao.mappers.jdbc.JDBCMapperFactory;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exptions.NoSuchUserException;
import ua.yaskal.model.exptions.NonUniqueEmailException;

import java.sql.*;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private Connection connection;
    private final static Logger logger = Logger.getLogger(JDBCUserDao.class);
    private MapperFactory mapperFactory = new JDBCMapperFactory();

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User getById(long id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
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
        try (PreparedStatement addUserStatement = connection.prepareStatement(UserRequests.ADD_USER, Statement.RETURN_GENERATED_KEYS)){
            addUserStatement.setString(1, item.getEmail());
            addUserStatement.setString(2, item.getName());
            addUserStatement.setString(3, item.getSurname());
            addUserStatement.setString(4, item.getPatronymic());
            addUserStatement.setString(5, item.getRole().getStringRole());
            addUserStatement.setString(6, item.getPassword());
            logger.error("Add user "+addUserStatement);
            addUserStatement.execute();

            ResultSet resultSet = addUserStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("User was not added: " + e);
            if (e.getErrorCode() == 1062){
                throw new NonUniqueEmailException();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (PreparedStatement getUserStatement = connection.prepareStatement(UserRequests.GET_USER_BY_EMAIL)) {
            getUserStatement.setString(1, email);

            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getUserMapper().mapToObject(resultSet);
            } else {
                throw new NoSuchUserException();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
