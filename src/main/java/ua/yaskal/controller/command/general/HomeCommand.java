package ua.yaskal.controller.command.general;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.Command;
import ua.yaskal.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * This command used to redirect to the appropriate page depending on the role of user.
 *
 * @author Nazar Yaskal
 */
public class HomeCommand implements Command {
    private final static Logger logger = Logger.getLogger(HomeCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();

        User user = (User) Optional.ofNullable(session.getAttribute("user"))
                .orElse(User.getBuilder().setUserRole(User.Role.GUEST).build());
        logger.trace("User " + user.getId() + " with role " + user.getRole() + " redirect to home page");
        switch (user.getRole()) {
            case USER:
                return "redirect:/mybank/api/user/home";
            case ADMIN:
                return "redirect:/mybank/api/admin/home";
            default:
                return "redirect:/mybank/api/guest/login";
        }
    }
}
