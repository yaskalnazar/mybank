package ua.yaskal.model.dao;

import java.util.List;

/**
 * General template for DAO that includes general crud operations.
 *
 * @param <T> Entity that are served by this dao
 * @author Nazar Yaskal
 */
public interface DAO<T> {
    T getById(long id);

    List<T> getAll();

    void update(T item);

    boolean delete(long id);

    long addNew(T item);


}
