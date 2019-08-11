package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.entity.Payment;

public class PaymentService {
    private DAOFactory daoFactory;

    public PaymentService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Payment addNew(Payment item){
        item.setId(daoFactory.createPaymentDAO().addNew(item));
        return item;
    }

}
