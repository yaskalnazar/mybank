package ua.yaskal.model.dao;

import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.User;

/**
 * This is extension of {@link DAO} template for {@link User} entity
 *
 * @author Nazar Yaskal
 * @see DAO
 * @see User
 */
public interface UserDAO extends DAO<User> {
    User getByEmail(String email);

    PaginationDTO<User> getAllPage(long itemsPerPage, long currentPage);

}
