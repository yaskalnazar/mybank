package org.itstep.controller.command.admin;

import org.itstep.controller.JspPath;
import org.itstep.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class AdminHome implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPath.ADMIN_HOME;
    }
}
