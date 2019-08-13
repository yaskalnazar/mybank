package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.entity.Transaction;
import ua.yaskal.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Realization of interface {@link Mapper} for entity {@link User}.
 *
 * @author Nazar Yaskal
 * @see CreditRequest
 * @see User
 */
public class JDBCUserMapper implements Mapper<User> {
    @Override
    public User mapToObject(ResultSet resultSet) throws SQLException {
        return User.getBuilder()
                .setId(resultSet.getLong("user_id"))
                .setEmail(resultSet.getString("user_email"))
                .setPassword(resultSet.getString("user_password_hash"))
                .setUserRole(User.Role.valueOf(resultSet.getString("user_role").toUpperCase()))
                .setName(resultSet.getString("user_name"))
                .setSurname(resultSet.getString("user_surname"))
                .setPatronymic(resultSet.getString("user_patronymic"))
                .build();
    }
}
