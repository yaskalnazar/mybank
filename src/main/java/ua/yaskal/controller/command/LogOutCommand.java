package ua.yaskal.controller.command;

import org.apache.log4j.Logger;
import ua.yaskal.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogOutCommand implements Command {
    private final static Logger logger = Logger.getLogger(LogOutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        logger.debug("User " + user.getId() + " logout");

        request.getSession().invalidate();
        return "redirect:/mybank/guest/login";
    }
}
