package org.itstep.model.dao.jdbc;

import org.apache.log4j.Logger;
import org.itstep.model.dao.UserDao;
import org.itstep.model.dao.jdbc.sql.requests.UserRequests;
import org.itstep.model.entity.User;
import org.itstep.model.exptions.NonUniqueEmailException;

import java.sql.*;
import java.util.List;

public class JDBCUserDao implements UserDao {
    private Connection connection;
    private final static Logger logger = Logger.getLogger(JDBCUserDao.class);


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
}
