package ua.yaskal.model.dao;

import ua.yaskal.model.dao.jdbc.JDBCDaoFactory;

/**
 * Abstract class for creating DAO instances.
 *
 * @author Nazar Yaskal
 */
public abstract class DAOFactory {
    private static DAOFactory daoFactory;

    public abstract UserDAO createUserDAO();

    public abstract DepositDAO createDepositDAO();

    public abstract CreditDAO createCreditDAO();

    public abstract CreditRequestDAO createCreditRequestDAO();

    public abstract TransactionDAO createTransactionDAO();

    public abstract AccountDAO createAccountDAO();

    public abstract PaymentDAO createPaymentDAO();

    /**
     * This method realize singleton pattern for DAOFactory
     *
     * @author Nazar Yaskal
     * @see DAOFactory
     */
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
