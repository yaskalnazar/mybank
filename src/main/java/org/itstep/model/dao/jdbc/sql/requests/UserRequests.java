package org.itstep.model.dao.jdbc.sql.requests;

public interface UserRequests {
    String ADD_USER = "insert into users ( user_email, user_name, user_surname, user_patronymic, user_role, user_password) values (?,?,?,?,?,?)";
}
