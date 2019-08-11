package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.PaymentDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.Payment;
import ua.yaskal.model.exceptions.no.such.NoSuchActiveAccountException;
import ua.yaskal.model.exceptions.no.such.NoSuchPaymentException;

import java.sql.*;
import java.util.ArrayList;
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.select.by.id"))) {
            statement.setString(1, id + "");

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
    public long addNew(Payment item) {
        try (PreparedStatement getActiveAccount = connection.prepareStatement(
                sqlRequestsBundle.getString("account.select.active.by.id"), Statement.RETURN_GENERATED_KEYS);
                PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getAmount().toString());
            statement.setString(2, item.getDate().toString());
            statement.setString(3, item.getPaymentStatus().name());
            statement.setString(4, item.getPayerAccountId()+"");
            statement.setString(5, item.getRequesterAccountId()+"");
            statement.setString(6, item.getMessage());

            getActiveAccount.setString(1, item.getPayerAccountId() + "");
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.select.by.payer.id"))) {
            statement.setString(1, payerId + "");

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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.select.by.requester.id"))) {
            statement.setString(1, requesterId + "");

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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("payment.update.status.by.id"))) {
            statement.setString(1, status.name());
            statement.setString(2, id + "");

            logger.debug("Update payment (id=" + id + ") status to " + status.name());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Can not update status",e);
            throw new RuntimeException(e);
        }
    }
}
