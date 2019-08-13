package ua.yaskal.model.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Generic interface for mappers from {@link ResultSet} to entities.
 *
 * @param <T> Targeted entity
 * @author Nazar Yaskal
 * @see ResultSet
 */
public interface Mapper<T> {
    T mapToObject(ResultSet resultSet) throws SQLException;
}
