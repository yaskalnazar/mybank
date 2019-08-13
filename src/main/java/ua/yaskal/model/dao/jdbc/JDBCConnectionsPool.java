package ua.yaskal.model.dao.jdbc;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class JDBCConnectionsPool {
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
                    basicDataSource.setMinIdle(5);
                    basicDataSource.setMaxIdle(10);
                    basicDataSource.setMaxOpenPreparedStatements(100);
                    dataSource = basicDataSource;
                }
            }

        }
        return dataSource;
    }
}
