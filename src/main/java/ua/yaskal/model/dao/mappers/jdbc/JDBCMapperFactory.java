package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.dao.mappers.MapperFactory;
import ua.yaskal.model.entity.User;

public class JDBCMapperFactory implements MapperFactory {
    @Override
    public Mapper<User> getUserMapper() {
        return new JDBCUserMapper();
    }
}
