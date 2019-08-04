package ua.yaskal.model.service;

import ua.yaskal.model.dao.CreditDAO;
import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.entity.CreditAccount;

import java.util.List;

public class CreditService {

    public List<CreditAccount> getAll(){
        CreditDAO creditDAO = DaoFactory.getInstance().createCreditDAO();
        return creditDAO.getAll();
    }

    public List<CreditAccount> getAllByOwnerId(long ownerId){
        CreditDAO creditDAO = DaoFactory.getInstance().createCreditDAO();
        return creditDAO.getAllByOwnerId(ownerId);
    }
}
