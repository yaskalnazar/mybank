package ua.yaskal.controller;

/**
 * This interface used for getting to frontend
 *
 * @author Nazar Yaskal
 */
public interface JspPath {
    String REG_FORM = "/WEB-INF/jsp/guest/regForm.jsp";
    String LOGIN_FORM = "/WEB-INF/jsp/guest/loginForm.jsp";
    String ERROR404 = "/WEB-INF/jsp/errors/404error.jsp";

    String USER_HOME = "/WEB-INF/jsp/user/userHome.jsp";
    String USER_DEPOSIT_OPEN = "/WEB-INF/jsp/user/depositOpen.jsp";
    String USER_CREDIT_OPEN = "/WEB-INF/jsp/user/newCreditRequest.jsp";
    String USER_ALL_ACCOUNTS = "/WEB-INF/jsp/user/allAccounts.jsp";
    String USER_REPLENISH_ACCOUNT = "/WEB-INF/jsp/user/replenishAccount.jsp";
    String USER_MAKE_TRANSACTION = "/WEB-INF/jsp/user/makeTransaction.jsp";
    String USER_MAKE_PAYMENT = "/WEB-INF/jsp/user/makePayment.jsp";
    String USER_ALL_PAYMENTS = "/WEB-INF/jsp/user/allPayments.jsp";
    String USER_CREDIT_PAGE = "/WEB-INF/jsp/user/userCreditPage.jsp";
    String USER_DEPOSIT_PAGE = "/WEB-INF/jsp/user/userDepositPage.jsp";


    String ADMIN_HOME = "/WEB-INF/jsp/admin/adminHome.jsp";
    String ADMIN_ALL_USERS = "/WEB-INF/jsp/admin/allUsers.jsp";
    String ADMIN_ALL_DEPOSITS = "/WEB-INF/jsp/admin/allDeposits.jsp";
    String ADMIN_ALL_CREDITS = "/WEB-INF/jsp/admin/allCredits.jsp";
    String ADMIN_ALL_CREDIT_REQUESTS = "/WEB-INF/jsp/admin/allCreditRequests.jsp";
    String ADMIN_CREDIT_REQUEST = "/WEB-INF/jsp/admin/creditRequestPage.jsp";
    String ADMIN_USER_PAGE = "/WEB-INF/jsp/admin/userPage.jsp";
    String ADMIN_CREDIT_PAGE = "/WEB-INF/jsp/admin/adminCreditPage.jsp";
    String ADMIN_DEPOSIT_PAGE = "/WEB-INF/jsp/admin/adminDepositPage.jsp";


}
