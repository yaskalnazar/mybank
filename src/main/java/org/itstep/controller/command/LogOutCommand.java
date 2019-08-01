package org.itstep.controller.command;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        //CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, "Guest");
        return "/index.jsp";
    }
}
