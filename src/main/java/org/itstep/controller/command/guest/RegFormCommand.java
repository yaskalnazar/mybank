package org.itstep.controller.command.guest;

import org.itstep.controller.JspPath;
import org.itstep.controller.command.Command;

import javax.servlet.http.HttpServletRequest;


public class RegFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPath.REG_FORM;
    }
}
