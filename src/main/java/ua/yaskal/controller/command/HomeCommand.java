package ua.yaskal.controller.command;

import org.apache.log4j.Logger;
import ua.yaskal.model.entity.User;

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
                return "redirect:/mybank/user/home";
            case ADMIN:
                return "redirect:/mybank/admin/home";
            default:
                return "redirect:/mybank/guest/login";
        }
    }
}