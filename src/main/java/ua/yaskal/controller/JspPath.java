package ua.yaskal.controller;

public interface JspPath {
    String REG_FORM = "/WEB-INF/jsp/guest/regForm.jsp";
    String LOGIN_FORM = "/WEB-INF/jsp/guest/loginForm.jsp";
    String ACCESS_DENIED = "/WEB-INF/jsp/accessDenied.jsp";
    String ERROR404="/WEB-INF/jsp/errors/404error.jsp";

    String USER_HOME = "/WEB-INF/jsp/user/userHome.jsp";
    String DEPOSIT_OPEN="/WEB-INF/jsp/user/depositOpen.jsp";
    String CREDIT_OPEN="/WEB-INF/jsp/user/newCreditRequest.jsp";
    String USER_ALL_ACCOUNTS="/WEB-INF/jsp/user/allAccounts.jsp";

    String ADMIN_HOME ="/WEB-INF/jsp/admin/adminHome.jsp";
    String ALL_USERS="/WEB-INF/jsp/admin/allUsers.jsp";
    String ADMIN_ALL_DEPOSITS="/WEB-INF/jsp/admin/allDeposits.jsp";


}
