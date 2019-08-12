package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.CreditRequestDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchCreditRequestException;

import java.sql.*;
import java.util.ArrayList;
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
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.select.by.id"))) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getCreditRequestMapper().mapToObject(resultSet);
            } else {
                logger.warn("No credit request with id:" + id);
                throw new NoSuchCreditRequestException();
            }
        } catch (SQLException e) {
            logger.error("Can not get credit request", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CreditRequest> getAll() {
        List<CreditRequest> creditRequests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.select.all"))) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditRequests.add(mapperFactory.getCreditRequestMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all credit requests", e);
            throw new RuntimeException(e);
        }
        return creditRequests;
    }

    @Override
    public void update(CreditRequest item) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.update.by.id"))) {
            statement.setBigDecimal(1, item.getCreditLimit());
            statement.setBigDecimal(2, item.getCreditRate());
            statement.setObject(3, item.getCreationDate());
            statement.setString(4, item.getCreditRequestStatus().name());
            statement.setLong(5, item.getApplicantId());
            statement.setLong(6, item.getId());

            logger.debug("Trying update credit" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Credit request was not updated", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean delete(long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.delete.by.id"))) {
            statement.setLong(1, id);

            logger.debug("Trying delete credit request " + statement);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error("Credit request was not deleted", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public long addNew(CreditRequest item) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(1, item.getCreditLimit());
            statement.setBigDecimal(2, item.getCreditRate());
            statement.setObject(3, item.getCreationDate());
            statement.setString(4, item.getCreditRequestStatus().name());
            statement.setLong(5, item.getApplicantId());

            logger.debug("Add new credit request " + statement);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Credit request was not added", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CreditRequest> getAllByStatus(CreditRequest.CreditRequestStatus status) {
        List<CreditRequest> creditRequests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.select.all.where.status"))) {
            statement.setString(1, status.name());

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditRequests.add(mapperFactory.getCreditRequestMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get all credit requests", e);
            throw new RuntimeException(e);
        }
        return creditRequests;
    }

    @Override
    public boolean updateStatusById(CreditRequest.CreditRequestStatus status, long id) {
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.change.status"))) {
            statement.setString(1, status.name());
            statement.setLong(2, id);

            logger.debug("Change credit request (id=" + id + ") status to " + status.name());
            statement.executeUpdate();

            return true;
        } catch (SQLException e) {
            logger.error("Can not change status", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CreditRequest> getAllByApplicantId(long applicantId) {
        List<CreditRequest> creditRequests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.select.all.by.applicant.id"))) {
            statement.setLong(1, applicantId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditRequests.add(mapperFactory.getCreditRequestMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get user credit requests", e);
            throw new RuntimeException(e);
        }
        return creditRequests;
    }

    @Override
    public List<CreditRequest> getAllByApplicantIdAndStatus(long applicantId, CreditRequest.CreditRequestStatus status) {
        List<CreditRequest> creditRequests = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("credit.request.select.all.by.applicant.id.and.status"))) {
            statement.setLong(1, applicantId);
            statement.setString(2, status.name());


            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                creditRequests.add(mapperFactory.getCreditRequestMapper().mapToObject(resultSet));
            }
        } catch (SQLException e) {
            logger.error("Can not get user credit requests", e);
            throw new RuntimeException(e);
        }
        return creditRequests;
    }
}
