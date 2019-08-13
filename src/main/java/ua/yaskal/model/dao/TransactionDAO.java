package ua.yaskal.model.dao;

import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.Transaction;

import java.util.List;

/**
 * This is extension of {@link DAO} template for {@link Transaction} entity
 *
 * @author Nazar Yaskal
 * @see DAO
 * @see Transaction
 */
public interface TransactionDAO extends DAO<Transaction> {
    List<Transaction> getAllByReceiverId(long id);
    List<Transaction> getAllBySenderId(long id);
    List<Transaction> getAllByAccountId(long id);
    PaginationDTO<Transaction> getPageByAccountId(long id, long itemsPerPage, long currentPage);

}
