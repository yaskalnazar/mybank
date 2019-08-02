package ua.yaskal.model.dao;

import ua.yaskal.model.entity.User;

public interface UserDao extends Dao<User> {
    User getByEmail(String email);
}
