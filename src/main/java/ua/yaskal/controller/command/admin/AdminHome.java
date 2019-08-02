package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class AdminHome implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPath.ADMIN_HOME;
    }
}
