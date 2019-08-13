package ua.yaskal.model.dao;

import ua.yaskal.model.entity.CreditRequest;

import java.util.List;

/**
 * This is extension of {@link DAO} template for {@link CreditRequest} entity
 *
 * @author Nazar Yaskal
 * @see DAO
 * @see CreditRequest
 */
public interface CreditRequestDAO extends DAO<CreditRequest> {
    List<CreditRequest> getAllByStatus(CreditRequest.CreditRequestStatus status);
    boolean updateStatusById(CreditRequest.CreditRequestStatus status, long id);
    List<CreditRequest> getAllByApplicantId(long applicantId);

    List<CreditRequest> getAllByApplicantIdAndStatus(long applicantId, CreditRequest.CreditRequestStatus status);
}
