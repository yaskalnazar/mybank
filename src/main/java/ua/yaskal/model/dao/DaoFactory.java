package ua.yaskal.model.dao;

import ua.yaskal.model.dao.jdbc.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDAO createUserDAO();

    public abstract DepositDAO createDepositDAO();

    public abstract CreditDAO createCreditDAO();

    public abstract CreditRequestDAO createCreditRequestDAO();

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                if (daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
