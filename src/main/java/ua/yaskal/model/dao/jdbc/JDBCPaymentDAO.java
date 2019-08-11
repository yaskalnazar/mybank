package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.PaymentDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Payment;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCPaymentDAO implements PaymentDAO {
    private final static Logger logger = Logger.getLogger(JDBCPaymentDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;

    public JDBCPaymentDAO(Connection connection, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public Payment getById(long id) {
       return null;
    }

    @Override
    public List<Payment> getAll() {
        return null;
    }

    @Override
    public void update(Payment item) {

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(Payment item) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getAmount().toString());
            statement.setString(2, item.getDate().toString());
            statement.setString(3, item.getPaymentStatus().name());
            statement.setString(4, item.getPayerAccountId()+"");
            statement.setString(5, item.getRequesterAccountId()+"");
            statement.setString(6, item.getMessage());


            logger.debug("Add new payment" + statement);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Payment was not added",e);
            throw new RuntimeException(e);
        }
    }
}
