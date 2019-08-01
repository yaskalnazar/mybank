package org.itstep.controller.Command;

import org.itstep.controller.JspPath;
import javax.servlet.http.HttpServletRequest;


public class RegFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPath.REG_FORM;
    }
}
