package ua.yaskal.model.dao;

import ua.yaskal.model.entity.CreditRequest;

import java.util.List;

public interface CreditRequestDAO extends DAO<CreditRequest> {
    List<CreditRequest> getAllByStatus(CreditRequest.CreditRequestStatus status);
    boolean changeStatus(CreditRequest.CreditRequestStatus status, long id);
    List<CreditRequest> getAllByApplicantId(long applicantId);

    List<CreditRequest> getAllByApplicantIdAndStatus(long applicantId, CreditRequest.CreditRequestStatus status);
}
