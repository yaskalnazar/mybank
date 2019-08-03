package ua.yaskal.model.dao;

import ua.yaskal.model.entity.User;

public interface UserDAO extends DAO<User> {
    User getByEmail(String email);
}
