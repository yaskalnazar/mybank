package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class AllUsersCommand implements Command {
    UserService userService = new UserService();
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("users", userService.getAllUsers());
        return JspPath.ALL_USERS;
    }
}
