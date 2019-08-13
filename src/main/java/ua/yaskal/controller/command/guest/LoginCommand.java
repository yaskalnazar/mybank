package ua.yaskal.controller.command.guest;


import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.UserLoginDTO;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.WrongPasswordException;
import ua.yaskal.model.exceptions.message.key.no.such.NoSuchUserException;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Objects;

/**
 * This command used for getting login_form for QUEST and process input.
 * Required params: email and password if QUEST SingIn;
 *
 * @author Nazar Yaskal
 */
public class LoginCommand implements Command {
    private final static Logger logger = Logger.getLogger(LoginCommand.class);
    private ValidationUtil validationUtil;
    private UserService userService;

    public LoginCommand(ValidationUtil validationUtil, UserService userService) {
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isContains(request, Arrays.asList("email", "password"))) {
            return JspPath.LOGIN_FORM;
        }

        if (!validationUtil.isRequestValid(request, Arrays.asList("email", "password"))) {
            request.setAttribute("wrongInput", true);
            return JspPath.LOGIN_FORM;
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO(
                request.getParameter("email"),
                request.getParameter("password"));
        User user;
        try {
            user = userService.loginUser(userLoginDTO);
        } catch (NoSuchUserException e) {
            logger.warn("Login attempt with nonexistent email " + userLoginDTO.getEmail());
            request.setAttribute("wrongInput", true);
            return JspPath.LOGIN_FORM;
        } catch (WrongPasswordException e) {
            logger.warn("Login attempt with wrong password (email: " + userLoginDTO.getEmail() + ")");
            request.setAttribute("wrongInput", true);
            return JspPath.LOGIN_FORM;
        }


        signInUser(request, user);
        return "redirect:/mybank/home";

    }

    private void signInUser(HttpServletRequest request, User user) {
        if (Objects.nonNull(request.getServletContext().getAttribute(user.getEmail()))) {
            ((HttpSession) request.getServletContext().getAttribute(user.getEmail())).invalidate();
            request.getServletContext().removeAttribute(user.getEmail());
            logger.warn("Remove another session of user " + user.getEmail());
        }

        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userId", user.getId());
        request.getSession().setAttribute("email", user.getEmail());
        request.getServletContext().setAttribute(user.getEmail(), request.getSession());
        logger.debug("User " + user.getId() + " successfully login");
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
