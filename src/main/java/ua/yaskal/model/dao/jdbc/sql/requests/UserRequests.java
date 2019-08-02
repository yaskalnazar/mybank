package ua.yaskal.model.dao.jdbc.sql.requests;

public interface UserRequests {
    String ADD_USER = "insert into users ( user_email, user_name, user_surname, user_patronymic, user_role, user_password) values (?,?,?,?,?,?)";
    String GET_USER_BY_EMAIL = "select * from users where user_email = ?";
}
