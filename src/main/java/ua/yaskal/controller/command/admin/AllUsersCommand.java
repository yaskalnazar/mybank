package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.PaginationDTO;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

public class AllUsersCommand implements Command {
    private static final long ITEMS_PER_PAGE = 15;
    private ValidationUtil validationUtil;
    private UserService userService;

    public AllUsersCommand(ValidationUtil validationUtil, UserService userService) {
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("page", getPage(request));
        return JspPath.ADMIN_ALL_USERS;
    }

    private PaginationDTO<User> getPage(HttpServletRequest request) {
        long currentPage = validationUtil.isContains(request, Collections.singletonList("currentPage"))
                ? Long.parseLong(request.getParameter("currentPage")) : 1;

        return userService.getAllPage(ITEMS_PER_PAGE, currentPage);
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
