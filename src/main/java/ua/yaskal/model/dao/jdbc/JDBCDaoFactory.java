package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.dao.UserDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {
    private final static Logger logger = Logger.getLogger(JDBCDaoFactory.class);
    private final String DB_URL = "jdbc:mysql://localhost:3306/mybankdb?serverTimezone=UTC";
    private final String DB_PASSWORD = "root";
    private final String DB_USER = "root";

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    private Connection getConnection() {
        try {
            logger.debug("Getting connection to DB URL:{" + DB_URL + "} Password:{" + DB_PASSWORD + "} User:{" + DB_USER + "}");
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD);
        } catch (
                SQLException e) {
            logger.error("Cannot get connection to db");
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            logger.error("Missing sql driver");
            throw new RuntimeException(e);
        }
    }


}
