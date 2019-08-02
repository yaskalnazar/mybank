package ua.yaskal.controller.command;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogOutCommand implements Command {
    private final static Logger logger = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/mybank/guest/login";
    }
}
