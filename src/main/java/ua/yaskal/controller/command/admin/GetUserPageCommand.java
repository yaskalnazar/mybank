package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.no.such.NoSuchUserException;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class GetUserPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private ValidationUtil validationUtil = new ValidationUtil();
    private UserService userService = new UserService();
    private CreditService creditService = new CreditService();
    private DepositService depositService = new DepositService();


    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isContains(request, Arrays.asList("id")) ||
                !validationUtil.isParamValid(request.getParameter("id"), "id")) {
            logger.warn("Incorrect id");
            throw new RuntimeException("Incorrect id " + request.getRequestURI());
        }

        User requestUser;
        try {
            requestUser = userService.getById(Long.parseLong(request.getParameter("id")));
        } catch (NoSuchUserException e){
            logger.warn("NoSuchUserException id: "+request.getParameter("id"));
            request.setAttribute("messageKey", e.getMessageKey());
            return JspPath.RESOURCE_NOT_EXIST;
        }
        request.setAttribute("requestUser", requestUser);
        request.setAttribute("credits", creditService.getAllByOwnerId(requestUser.getId()));
        request.setAttribute("deposits", depositService.getAllByOwnerId(requestUser.getId()));

        return JspPath.USER_PAGE;
    }
}