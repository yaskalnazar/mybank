package org.itstep.controller.command;

import org.apache.log4j.Logger;
import org.itstep.controller.filters.AuthFilter;
import org.itstep.model.entity.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LogOutCommand implements Command {
    private final static Logger logger = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/mybank/guest/login_form";
    }
}
