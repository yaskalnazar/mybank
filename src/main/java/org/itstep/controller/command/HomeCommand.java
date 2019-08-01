package org.itstep.controller.command;

import org.apache.log4j.Logger;
import org.itstep.controller.JspPath;
import org.itstep.model.entity.User;
import org.itstep.model.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;


public class HomeCommand implements Command {
    private final static Logger logger = Logger.getLogger(HomeCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        User user = (User) Optional.ofNullable(session.getAttribute("user"))
                .orElse(User.getBuilder().setUserRole(User.Role.GUEST).build());
        logger.debug("User " + user.getRole());
        switch (user.getRole()) {
            case USER:
                return JspPath.USER_HOME;
            case ADMIN:
                return JspPath.ADMIN_HOME;
            default:
                return JspPath.LOGIN_FORM;
        }
    }
}
