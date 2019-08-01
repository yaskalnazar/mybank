package org.itstep.controller.command.user;

import org.itstep.controller.JspPath;
import org.itstep.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class UserHome implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPath.USER_HOME;
    }
}
