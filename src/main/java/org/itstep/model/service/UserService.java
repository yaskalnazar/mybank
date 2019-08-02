package org.itstep.model.service;

import org.apache.log4j.Logger;
import org.itstep.model.dao.DaoFactory;
import org.itstep.model.dao.UserDao;
import org.itstep.model.dto.UserDTO;
import org.itstep.model.entity.User;

import java.util.ArrayList;

public class UserService {
    private final static Logger logger = Logger.getLogger(UserService.class);


    public User addNewUser(UserDTO userDTO){
        UserDao userDao = DaoFactory.getInstance().createUserDao();
        User user = User.getBuilder()
                .setEmail(userDTO.getEmail())
                .setName(userDTO.getName())
                .setSurname(userDTO.getSurname())
                .setPatronymic(userDTO.getPatronymic())
                .setPassword(userDTO.getPassword())
                .setUserRole(User.Role.USER)
                .setAccounts(new ArrayList<>())
                .build();
        user.setId(userDao.addNew(user));

        return user;
    }

}
