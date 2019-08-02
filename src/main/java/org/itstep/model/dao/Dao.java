package org.itstep.model.dao;

import org.itstep.model.exptions.NonUniqueEmailException;

import java.util.List;

public interface Dao<T>{
    T getById(long id);
    List<T> getAll();
    void update(T item);
    boolean delete(long id);
    Long addNew(T item) throws NonUniqueEmailException;


}
