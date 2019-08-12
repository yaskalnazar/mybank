package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchUserException;
import ua.yaskal.model.service.CreditService;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class GetUserPageCommand implements Command {
    private final static Logger logger = Logger.getLogger(GetUserPageCommand.class);
    private ValidationUtil validationUtil;
    private UserService userService;
    private CreditService creditService;
    private DepositService depositService;

    public GetUserPageCommand(ValidationUtil validationUtil, UserService userService,
                              CreditService creditService, DepositService depositService) {
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.creditService = creditService;
        this.depositService = depositService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isContains(request, Arrays.asList("id")) ||
                !validationUtil.isParamValid(request.getParameter("id"), "id")) {
            logger.warn("Incorrect id");
            throw new RuntimeException("Incorrect id " + request.getRequestURI());
        }

        User requestUser = userService.getById(Long.parseLong(request.getParameter("id")));

        request.setAttribute("requestUser", requestUser);
        request.setAttribute("credits", creditService.getAllByOwnerId(requestUser.getId()));
        request.setAttribute("deposits", depositService.getAllByOwnerId(requestUser.getId()));

        return JspPath.ADMIN_USER_PAGE;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCreditService(CreditService creditService) {
        this.creditService = creditService;
    }

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }
}
