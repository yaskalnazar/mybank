package ua.yaskal.model.dao.jdbc;


import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.util.ResourceBundle;

/**
 * This class provides connections to database using JDBC and BasicDataSource.
 * Access provided with singleton pattern.
 *
 * @author Nazar Yaskal
 */
public class JDBCConnectionsPool {
    private final static Logger logger = Logger.getLogger(JDBCConnectionsPool.class);
    private static ResourceBundle databaseProperties = ResourceBundle.getBundle("database");
    private volatile static DataSource dataSource;


    static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (JDBCDaoFactory.class) {
                if (dataSource == null) {
                    BasicDataSource basicDataSource = new BasicDataSource();
                    basicDataSource.setDriverClassName(databaseProperties.getString("db.connection.driver"));
                    basicDataSource.setUrl(databaseProperties.getString("db.connection.datasource.url"));
                    basicDataSource.setUsername(databaseProperties.getString("db.connection.user"));
                    basicDataSource.setPassword(databaseProperties.getString("db.connection.password"));
                    basicDataSource.setMinIdle(Integer.parseInt(
                            databaseProperties.getString("db.connection.min.idle")));
                    basicDataSource.setMaxIdle(Integer.parseInt(
                            databaseProperties.getString("db.connection.max.idle")));
                    basicDataSource.setMaxOpenPreparedStatements(Integer.parseInt(
                            databaseProperties.getString("db.connection.min.open.prepared.statements")));
                    logger.debug("Getting DataSource " + basicDataSource.getUrl());
                    dataSource = basicDataSource;
                }
            }

        }
        return dataSource;
    }
}
