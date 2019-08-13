package ua.yaskal.model.dao.mappers;

import ua.yaskal.model.entity.*;

/**
 * Interface for creating {@link Mapper} instances.
 *
 * @author Nazar Yaskal
 * @see Mapper
 */
public interface MapperFactory {
    Mapper<User> getUserMapper();

    Mapper<DepositAccount> getDepositMapper();

    Mapper<CreditAccount> getCreditMapper();

    Mapper<CreditRequest> getCreditRequestMapper();

    Mapper<Account> getAccountMapper();

    Mapper<Transaction> getTransactionMapper();

    Mapper<Payment> getPaymentMapper();

}
