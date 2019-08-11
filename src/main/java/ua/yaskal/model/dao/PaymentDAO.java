package ua.yaskal.model.dao;

import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.entity.Payment;

import java.util.List;

public interface PaymentDAO extends DAO<Payment> {
    List<Payment> getAllByPayerId(long payerId);
    List<Payment> getAllByRequesterId(long requesterId);
    boolean updateStatusById(Payment.PaymentStatus status, long id);
}
