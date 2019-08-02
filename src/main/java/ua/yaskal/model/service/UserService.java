package ua.yaskal.model.service;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.dao.UserDao;
import ua.yaskal.model.dto.UserLoginDTO;
import ua.yaskal.model.dto.UserRegistrationDTO;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exptions.WrongPasswordException;

import java.util.ArrayList;

public class UserService {
    private final static Logger logger = Logger.getLogger(UserService.class);


    public User addNewUser(UserRegistrationDTO userDTO){
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

    public User loginUser(UserLoginDTO userDTO){
        UserDao userDao = DaoFactory.getInstance().createUserDao();
        User user = userDao.getByEmail(userDTO.getEmail());

        if (!userDTO.getPassword().equals(user.getPassword())){
            throw new WrongPasswordException();
        } else {
            return user;
        }
    }

}