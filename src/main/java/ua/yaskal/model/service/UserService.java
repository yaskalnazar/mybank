package ua.yaskal.model.service;

import org.apache.commons.codec.digest.DigestUtils;
import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.dto.UserLoginDTO;
import ua.yaskal.model.dto.UserRegistrationDTO;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.WrongPasswordException;

import java.util.ArrayList;
import java.util.List;

/**
 * This service used for working with user instance.
 *
 * @author Nazar Yaskal
 */
public class UserService {
    private DAOFactory daoFactory;

    public UserService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public User addNewUser(UserRegistrationDTO userDTO) {
        User user = User.getBuilder()
                .setEmail(userDTO.getEmail())
                .setName(userDTO.getName())
                .setSurname(userDTO.getSurname())
                .setPatronymic(userDTO.getPatronymic())
                .setPassword(userDTO.getPassword())
                .setUserRole(User.Role.USER)
                .setAccounts(new ArrayList<>())
                .build();
        user.setId(daoFactory.createUserDAO().addNew(user));

        return user;
    }

    public User loginUser(UserLoginDTO userDTO) {
        User user = daoFactory.createUserDAO().getByEmail(userDTO.getEmail());

        if (!DigestUtils.sha256Hex(userDTO.getPassword()).equals(user.getPassword())) {
            throw new WrongPasswordException();
        } else {
            return user;
        }
    }

    public List<User> getAllUsers() {
        return daoFactory.createUserDAO().getAll();
    }

    public User getById(long id) {
        return daoFactory.createUserDAO().getById(id);
    }

    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public PaginationDTO<User> getAllPage(long itemsPerPage, long currentPage){
        return daoFactory.createUserDAO().getAllPage(itemsPerPage,currentPage);
    }

}
