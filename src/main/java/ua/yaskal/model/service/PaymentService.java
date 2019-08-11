package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.entity.Payment;

import java.util.List;

public class PaymentService {
    private DAOFactory daoFactory;

    public PaymentService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Payment addNew(Payment item){
        item.setId(daoFactory.createPaymentDAO().addNew(item));
        return item;
    }

    public Payment getById(long id){
        return daoFactory.createPaymentDAO().getById(id);
    }

    public boolean updateStatusById(Payment.PaymentStatus status, long id){
        return daoFactory.createPaymentDAO().updateStatusById(status,id);
    }

    public List<Payment> getAllByPayerId(long payerId){
        return daoFactory.createPaymentDAO().getAllByPayerId(payerId);
    };
    public List<Payment> getAllByRequesterId(long requesterId){
        return daoFactory.createPaymentDAO().getAllByRequesterId(requesterId);
    }

}
