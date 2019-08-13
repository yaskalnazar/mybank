package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.UserDAO;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.NonUniqueEmailException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchPageException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchUserException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCUserDAO implements UserDAO {
    private final static Logger logger = Logger.getLogger(JDBCUserDAO.class);
    private DataSource dataSource;
    private ResourceBundle sqlRequestsBundle;
    private MapperFactory mapperFactory;

    public JDBCUserDAO(DataSource dataSource, ResourceBundle sqlRequestsBundle, MapperFactory mapperFactory) {
        this.dataSource = dataSource;
        this.sqlRequestsBundle = sqlRequestsBundle;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public User getById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(
                sqlRequestsBundle.getString("user.select.by.id"))) {
            getUserStatement.setLong(1, id);

            logger.debug("Select user " + getUserStatement);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getUserMapper().mapToObject(resultSet);
            } else {
                logger.debug("No user with id:" + id);
                throw new NoSuchUserException();
            }
        } catch (SQLException e) {
            logger.error("Can not get user", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     sqlRequestsBundle.getString("user.select.all"))) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(mapperFactory.getUserMapper().mapToObject(resultSet));
            }
            return users;
        } catch (SQLException e) {
            logger.error("Can not get all users", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User item) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                sqlRequestsBundle.getString("user.update.by.id"))) {
            statement.setString(1, item.getEmail());
            statement.setString(2, item.getName());
            statement.setString(3, item.getSurname());
            statement.setString(4, item.getPatronymic());
            statement.setString(5, item.getRole().getStringRole());
            statement.setString(6, item.getPassword());
            statement.setLong(7, item.getId());


            logger.debug("Trying update user" + statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("User was not updated", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long addNew(User item) throws NonUniqueEmailException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement addUserStatement = connection.prepareStatement(
                sqlRequestsBundle.getString("user.insert.new"), Statement.RETURN_GENERATED_KEYS)) {
            addUserStatement.setString(1, item.getEmail());
            addUserStatement.setString(2, item.getName());
            addUserStatement.setString(3, item.getSurname());
            addUserStatement.setString(4, item.getPatronymic());
            addUserStatement.setString(5, item.getRole().getStringRole());
            addUserStatement.setString(6, item.getPassword());

            logger.debug("Add new user " + addUserStatement);
            addUserStatement.execute();

            ResultSet resultSet = addUserStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("User was not added", e);
            if (e.getErrorCode() == 1062) {
                throw new NonUniqueEmailException();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement getUserStatement = connection.prepareStatement(
                sqlRequestsBundle.getString("user.select.by.email"))) {
            getUserStatement.setString(1, email);

            logger.debug("Select user " + getUserStatement);
            ResultSet resultSet = getUserStatement.executeQuery();
            if (resultSet.next()) {
                return mapperFactory.getUserMapper().mapToObject(resultSet);
            } else {
                logger.debug("No user with email:" + email);
                throw new NoSuchUserException();
            }
        } catch (SQLException e) {
            logger.error("Can not get user", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public PaginationDTO<User> getAllPage(long itemsPerPage, long currentPage) {
        PaginationDTO<User> paginationDTO = new PaginationDTO<>();
        try (Connection connection = dataSource.getConnection()){
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            try (PreparedStatement countDeposits = connection.prepareStatement(
                    sqlRequestsBundle.getString("user.count.all"));
                 PreparedStatement getDeposits = connection.prepareStatement(
                         sqlRequestsBundle.getString("user.get.page.all"))) {

                long depositsNumber;
                ResultSet resultSet = countDeposits.executeQuery();
                if (resultSet.next()) {
                    depositsNumber = resultSet.getInt("number");
                } else {
                    throw new SQLException();
                }

                long pagesNumber = depositsNumber % itemsPerPage == 0 ? depositsNumber / itemsPerPage : (depositsNumber / itemsPerPage) + 1;


                if (pagesNumber == 0) {
                    paginationDTO.setPagesNumber(1);
                    paginationDTO.setCurrentPage(1);
                    paginationDTO.setItemsPerPage(itemsPerPage);
                    paginationDTO.setItems(Collections.emptyList());

                    connection.commit();

                    return paginationDTO;
                }

                if (currentPage > pagesNumber) {
                    throw new NoSuchPageException();
                }

                long offset = (currentPage - 1) * itemsPerPage;

                getDeposits.setLong(1, itemsPerPage);
                getDeposits.setLong(2, offset);

                logger.debug("Trying increase get users page "+getDeposits);
                resultSet = getDeposits.executeQuery();

                List<User> users = new ArrayList<>();
                while (resultSet.next()) {
                    users.add(mapperFactory.getUserMapper().mapToObject(resultSet));
                }

                paginationDTO.setPagesNumber(pagesNumber);
                paginationDTO.setCurrentPage(currentPage);
                paginationDTO.setItemsPerPage(itemsPerPage);
                paginationDTO.setItems(users);

                connection.commit();

                return paginationDTO;

            } catch (SQLException e) {
                connection.rollback();

                logger.error(e);
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
    }
}
