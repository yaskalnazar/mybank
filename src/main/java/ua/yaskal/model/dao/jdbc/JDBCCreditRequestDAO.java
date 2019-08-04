package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.CreditRequestDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.CreditRequest;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCCreditRequestDAO implements CreditRequestDAO {
    private final static Logger logger = Logger.getLogger(JDBCCreditRequestDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;

    public JDBCCreditRequestDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public CreditRequest getById(long id) {
        return null;
    }

    @Override
    public List<CreditRequest> getAll() {
        return null;
    }

    @Override
    public void update(CreditRequest item) {

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(CreditRequest item) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.insert.new"), Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, item.getCreditLimit().toString());
            statement.setString(2, item.getCreditRate().toString());
            statement.setString(3, item.getCreationDate().toString());
            statement.setString(4, item.getCreditRequestStatus().name());
            statement.setString(5, item.getApplicantId()+"");

            logger.debug("Add new credit request "+ statement);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Credit request was not added: " + e);
            throw new RuntimeException(e);
        }
    }
}
