package ua.yaskal.model.dao.mappers.jdbc;

import ua.yaskal.model.dao.mappers.Mapper;
import ua.yaskal.model.entity.CreditRequest;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCCreditRequestMapper implements Mapper<CreditRequest> {
    @Override
    public CreditRequest mapToObject(ResultSet resultSet) throws SQLException {
        return CreditRequest.getBuilder()
                .setId(resultSet.getLong("request_id"))
                .setCreationDate(resultSet.getDate("creation_date").toLocalDate())
                .setCreditLimit(resultSet.getBigDecimal("credit_limit"))
                .setCreditRate(resultSet.getBigDecimal("credit_rate"))
                .setCreditRequestStatus(CreditRequest.CreditRequestStatus.valueOf(
                        resultSet.getString("credit_request_status")))
                .setApplicantId(resultSet.getLong("applicant_user_id"))
                .build();
    }
}
