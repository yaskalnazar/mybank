package ua.yaskal.model.dao.mappers;

import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.User;

public interface MapperFactory {
    Mapper<User> getUserMapper();
    Mapper<DepositAccount> getDepositMapper();
}
