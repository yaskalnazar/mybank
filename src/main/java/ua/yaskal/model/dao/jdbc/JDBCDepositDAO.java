package ua.yaskal.model.dao.jdbc;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DepositDAO;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.exptions.NonUniqueEmailException;

import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class JDBCDepositDAO implements DepositDAO {
    private final static Logger logger = Logger.getLogger(JDBCDepositDAO.class);
    private Connection connection;
    private ResourceBundle sqlRequestsBundle;

    public JDBCDepositDAO(Connection connection, ResourceBundle sqlRequestsBundle) {
        this.connection = connection;
        this.sqlRequestsBundle = sqlRequestsBundle;
    }

    @Override
    public DepositAccount getById(long id) {
        return null;
    }

    @Override
    public List<DepositAccount> getAll() {
        return null;
    }

    @Override
    public void update(DepositAccount item) {

    }

    @Override
    public boolean delete(long id) {
        return false;
    }

    @Override
    public Long addNew(DepositAccount item) {
        try (PreparedStatement addUserStatement = connection.prepareStatement(
                sqlRequestsBundle.getString("deposit.insert.new"), Statement.RETURN_GENERATED_KEYS)){
            addUserStatement.setString(1, item.getAccountType().name());
            addUserStatement.setString(2, item.getBalance().toString());
            addUserStatement.setString(3, item.getClosingDate().toString());
            addUserStatement.setString(4, item.getOwner().getId()+"");
            addUserStatement.setString(5, item.getAccountStatus().name());
            addUserStatement.setString(6, item.getDepositAmount().toString());
            addUserStatement.setString(7, item.getDepositRate().toString());
            addUserStatement.setString(8, item.getDepositEndDate().toString());


            logger.debug("Add new Deposit Account "+ addUserStatement);
            addUserStatement.execute();

            ResultSet resultSet = addUserStatement.getGeneratedKeys();

            if (resultSet.next()) {
                return resultSet.getLong(1);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            logger.error("Deposit Account was not added: " + e);
            throw new RuntimeException(e);
        }
    }
}
