package ua.yaskal.model.dao;

import ua.yaskal.model.exptions.NonUniqueEmailException;

import java.util.List;

public interface Dao<T>{
    T getById(long id);
    List<T> getAll();
    void update(T item);
    boolean delete(long id);
    Long addNew(T item) throws NonUniqueEmailException;


}
