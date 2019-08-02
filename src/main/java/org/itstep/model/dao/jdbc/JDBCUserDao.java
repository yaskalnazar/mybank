package org.itstep.model.dao.jdbc;

import org.apache.log4j.Logger;
import org.itstep.model.dao.UserDao;
import org.itstep.model.dao.jdbc.sql.requests.UserRequests;
import org.itstep.model.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
    public boolean addNew(User item) {
        try (PreparedStatement addUser = connection.prepareStatement(UserRequests.ADD_USER)){
            addUser.setString(1, item.getEmail());
            addUser.setString(2, item.getName());
            addUser.setString(3, item.getSurname());
            addUser.setString(4, item.getPatronymic());
            addUser.setString(5, item.getRole().getStringRole());
            addUser.setString(6, item.getPassword());
            logger.error("Add user "+addUser);
            addUser.execute();
            return true;
        } catch (SQLException e) {
            logger.error("User was not added: " + e);
            return false;
        }
    }
}
