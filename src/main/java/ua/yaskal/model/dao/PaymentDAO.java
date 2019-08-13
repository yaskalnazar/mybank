package ua.yaskal.model.dao;

import ua.yaskal.model.entity.Payment;

import java.util.List;

/**
 * This is extension of {@link DAO} template for {@link Payment} entity
 *
 * @author Nazar Yaskal
 * @see DAO
 * @see Payment
 */
public interface PaymentDAO extends DAO<Payment> {
    List<Payment> getAllByPayerId(long payerId);
    List<Payment> getAllByRequesterId(long requesterId);
    boolean updateStatusById(Payment.PaymentStatus status, long id);
}
