package ua.yaskal.controller.command;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
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


        String locale = (String) request.getSession().getAttribute("locale");
        request.getSession().invalidate();
        request.getSession().setAttribute("locale", locale);
        //request.setAttribute("logoutSuccessfully", "You successfully logout");
        //return JspPath.LOGIN_FORM;
        return "redirect:/mybank/guest/login";

    }
}
