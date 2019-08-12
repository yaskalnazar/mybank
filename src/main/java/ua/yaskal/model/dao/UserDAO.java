package ua.yaskal.model.dao;

import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.User;

public interface UserDAO extends DAO<User> {
    User getByEmail(String email);
    PaginationDTO<User> getAllPage(long itemsPerPage, long currentPage);

}
