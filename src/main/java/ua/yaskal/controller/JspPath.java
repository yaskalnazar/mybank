package ua.yaskal.controller;

public interface JspPath {
    String REG_FORM = "/WEB-INF/jsp/guest/regForm.jsp";
    String LOGIN_FORM = "/WEB-INF/jsp/guest/loginForm.jsp";
    String ACCESS_DENIED = "/WEB-INF/jsp/accessDenied.jsp";
    String ERROR404="/WEB-INF/jsp/errors/404error.jsp";
    String RESOURCE_NOT_EXIST="/WEB-INF/jsp/errors/resourceNotExist.jsp";

    String USER_HOME = "/WEB-INF/jsp/user/userHome.jsp";
    String DEPOSIT_OPEN="/WEB-INF/jsp/user/depositOpen.jsp";
    String CREDIT_OPEN="/WEB-INF/jsp/user/newCreditRequest.jsp";
    String USER_ALL_ACCOUNTS="/WEB-INF/jsp/user/allAccounts.jsp";
    String REPLENISH_ACCOUNT = "/WEB-INF/jsp/user/replenishAccount.jsp";
    String MAKE_TRANSACTION = "/WEB-INF/jsp/user/makeTransaction.jsp";




    String ADMIN_HOME ="/WEB-INF/jsp/admin/adminHome.jsp";
    String ALL_USERS="/WEB-INF/jsp/admin/allUsers.jsp";
    String ADMIN_ALL_DEPOSITS="/WEB-INF/jsp/admin/allDeposits.jsp";
    String ADMIN_ALL_CREDITS="/WEB-INF/jsp/admin/allCredits.jsp";
    String ALL_CREDIT_REQUESTS="/WEB-INF/jsp/admin/allCreditRequests.jsp";
    String ADMIN_CREDIT_REQUEST="/WEB-INF/jsp/admin/creditRequestPage.jsp";
    String USER_PAGE="/WEB-INF/jsp/admin/userPage.jsp";

}
