package ua.yaskal.controller.command.user;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class UserHomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPath.USER_HOME;
    }
}
