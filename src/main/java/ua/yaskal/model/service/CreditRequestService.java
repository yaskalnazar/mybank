package ua.yaskal.model.service;

import org.apache.log4j.Logger;
import ua.yaskal.model.dao.CreditRequestDAO;
import ua.yaskal.model.dao.DaoFactory;
import ua.yaskal.model.dto.CreditRequestDTO;
import ua.yaskal.model.entity.CreditRequest;

import java.util.List;

public class CreditRequestService {
    private final static Logger logger = Logger.getLogger(CreditRequestService.class);

    public CreditRequest createNew(CreditRequestDTO creditRequestDTO){
        CreditRequestDAO creditRequestDAO = DaoFactory.getInstance().createCreditRequestDAO();
        CreditRequest creditRequest = CreditRequest.getBuilder()
                .setApplicantId(creditRequestDTO.getApplicantId())
                .setCreditRate(creditRequestDTO.getCreditRate())
                .setCreditLimit(creditRequestDTO.getCreditLimit())
                .setCreditRequestStatus(CreditRequest.CreditRequestStatus.PENDING)
                .setCreationDate(creditRequestDTO.getCreationDate())
                .build();

        creditRequest.setId(creditRequestDAO.addNew(creditRequest));
        return creditRequest;
    }

    public List<CreditRequest> getAll(){
        CreditRequestDAO creditRequestDAO = DaoFactory.getInstance().createCreditRequestDAO();
        return creditRequestDAO.getAll();
    }


    public List<CreditRequest> getAllByStatus(CreditRequest.CreditRequestStatus status){
        CreditRequestDAO creditRequestDAO = DaoFactory.getInstance().createCreditRequestDAO();
        return creditRequestDAO.getAllByStatus(status);
    }
}
