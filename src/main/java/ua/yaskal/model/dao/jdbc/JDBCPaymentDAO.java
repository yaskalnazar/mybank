package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.PaymentDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Payment;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchActiveAccountException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchPaymentException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class JDBCPaymentDAO implements PaymentDAO {
    private final static Logger logger = Logger.getLogger(JDBCPaymentDAO.class);
    private DataSource dataSource;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;

    public JDBCPaymentDAO(DataSource dataSource, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.dataSource = dataSource;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public Payment getById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.select.by.id"))) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getPaymentMapper().mapToObject(resultSet);
            } else {
                logger.warn("No payment with id:" + id);
                throw new NoSuchPaymentException();
            }
        } catch (SQLException e) {
            logger.error("Can not get payment", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.select.all"))) {

            logger.debug("Getting all payments");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                payments.add(mapperFactory.getPaymentMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all payments", e);
            throw new RuntimeException(e);
        }
        return payments;
    }

    @Override
    public void update(Payment item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.update.by.id"))) {
            statement.setBigDecimal(1, item.getAmount());
            statement.setObject(2, item.getDate());
            statement.setString(3, item.getPaymentStatus().name());
            statement.setLong(4, item.getPayerAccountId());
            statement.setLong(5, item.getRequesterAccountId());
            statement.setString(6, item.getMessage());
            statement.setLong(7, item.getId());

            logger.debug("Trying update payment" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Payment was not updated", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.delete.by.id"))) {
            statement.setLong(1, id);

            logger.debug("Trying delete payment " + statement);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Payment was not deleted", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public long addNew(Payment item) {
        try (   Connection connection = dataSource.getConnection();
                PreparedStatement getActiveAccount = connection.prepareStatement(
                sqlRequestsBundle.getString("account.select.active.by.id"), Statement.RETURN_GENERATED_KEYS);
                PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(1, item.getAmount());
            statement.setObject(2, item.getDate());
            statement.setString(3, item.getPaymentStatus().name());
            statement.setLong(4, item.getPayerAccountId());
            statement.setLong(5, item.getRequesterAccountId());
            statement.setString(6, item.getMessage());

            getActiveAccount.setLong(1, item.getPayerAccountId());
            logger.debug("Select receiver account " + getActiveAccount);
            ResultSet resultSet = getActiveAccount.executeQuery();
            if (!resultSet.next()) {
                logger.debug("No active account with id:" + item.getPayerAccountId());
                throw new NoSuchActiveAccountException();
            }


            logger.debug("Add new payment" + statement);
            statement.execute();

            resultSet = statement.getGeneratedKeys();

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

    @Override
    public List<Payment> getAllByPayerId(long payerId) {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.select.by.payer.id"))) {
            statement.setLong(1, payerId);

            logger.debug("Getting all by payerId" + payerId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                payments.add(mapperFactory.getPaymentMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get payments", e);
            throw new RuntimeException(e);
        }
        return payments;
    }

    @Override
    public List<Payment> getAllByRequesterId(long requesterId) {
        List<Payment> payments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.select.by.requester.id"))) {
            statement.setLong(1, requesterId);

            logger.debug("Getting all by requesterId" + requesterId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                payments.add(mapperFactory.getPaymentMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get payments", e);
            throw new RuntimeException(e);
        }
        return payments;
    }

    @Override
    public boolean updateStatusById(Payment.PaymentStatus status, long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.update.status.by.id"))) {
            statement.setString(1, status.name());
            statement.setLong(2, id);

            logger.debug("Update payment (id=" + id + ") status to " + status.name());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can not update status",e);
            throw new RuntimeException(e);
        }
    }
}
