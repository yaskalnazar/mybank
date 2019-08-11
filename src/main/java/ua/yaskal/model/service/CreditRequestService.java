package ua.yaskal.model.service;

import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.dto.CreditRequestDTO;
import ua.yaskal.model.entity.CreditRequest;

import java.util.List;

public class CreditRequestService {
    private DAOFactory daoFactory;

    public CreditRequestService(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public CreditRequest createNew(CreditRequestDTO creditRequestDTO) {
        CreditRequest creditRequest = CreditRequest.getBuilder()
                .setApplicantId(creditRequestDTO.getApplicantId())
                .setCreditRate(creditRequestDTO.getCreditRate())
                .setCreditLimit(creditRequestDTO.getCreditLimit())
                .setCreditRequestStatus(CreditRequest.CreditRequestStatus.PENDING)
                .setCreationDate(creditRequestDTO.getCreationDate())
                .build();

        creditRequest.setId(daoFactory.createCreditRequestDAO().addNew(creditRequest));
        return creditRequest;
    }

    public List<CreditRequest> getAll() {
        return daoFactory.createCreditRequestDAO().getAll();
    }


    public List<CreditRequest> getAllByStatus(CreditRequest.CreditRequestStatus status) {
        return daoFactory.createCreditRequestDAO().getAllByStatus(status);
    }

    public CreditRequest getById(long id) {
        return daoFactory.createCreditRequestDAO().getById(id);
    }

    public boolean changeStatus(CreditRequest.CreditRequestStatus status, long id) {
        return daoFactory.createCreditRequestDAO().updateStatusById(status, id);
    }

    public List<CreditRequest> getAllByApplicantId(long applicantId){
        return daoFactory.createCreditRequestDAO().getAllByApplicantId(applicantId);
    }

    public  List<CreditRequest> getAllByApplicantIdAndStatus(long applicantId, CreditRequest.CreditRequestStatus status){
        return daoFactory.createCreditRequestDAO().getAllByApplicantIdAndStatus(applicantId, status);
    }


    public void setDaoFactory(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
}
