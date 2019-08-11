package ua.yaskal.model.dao;

import ua.yaskal.model.dao.jdbc.JDBCDaoFactory;

public abstract class DAOFactory {
    private static DAOFactory daoFactory;

    public abstract UserDAO createUserDAO();

    public abstract DepositDAO createDepositDAO();

    public abstract CreditDAO createCreditDAO();

    public abstract CreditRequestDAO createCreditRequestDAO();

    public abstract TransactionDAO createTransactionDAO();

    public abstract AccountDAO createAccountDAO();

    public abstract PaymentDAO createPaymentDAO();


    public static DAOFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DAOFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
