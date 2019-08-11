package ua.yaskal.model.dao.mappers;

import ua.yaskal.model.entity.*;

public interface MapperFactory {
    Mapper<User> getUserMapper();
    Mapper<DepositAccount> getDepositMapper();
    Mapper<CreditAccount> getCreditMapper();
    Mapper<CreditRequest> getCreditRequestMapper();
    Mapper<Account> getAccountMapper();
    Mapper<Transaction> getTransactionMapper();
    Mapper<Payment> getPaymentMapper();

}
