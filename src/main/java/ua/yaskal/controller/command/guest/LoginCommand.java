package ua.yaskal.controller.command.guest;


import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.UserLoginDTO;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exptions.NoSuchUserException;
import ua.yaskal.model.exptions.WrongPasswordException;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class LoginCommand implements Command {
    private final static Logger logger = Logger.getLogger(LoginCommand.class);

    private ValidationUtil validationUtil = new ValidationUtil();
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isСontain(request, Arrays.asList("email", "password"))){
            return JspPath.LOGIN_FORM;
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO(
                request.getParameter("email"),
                request.getParameter("password"));
        User user;
        try {
             user = userService.loginUser(userLoginDTO);
        } catch (NoSuchUserException e){
            logger.warn("Login attempt with nonexistent email "+ userLoginDTO.getEmail());
            request.setAttribute("wrongInput", "wrongInput");
            return JspPath.LOGIN_FORM;
        } catch (WrongPasswordException e){
            logger.warn("Login attempt with wrong password "+ userLoginDTO.getEmail());
            request.setAttribute("wrongInput", "wrongInput");
            return JspPath.LOGIN_FORM;
        }



        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userid", user.getId());
        logger.debug("User " + user.getId()+" successfully login");
        return "redirect:/mybank/home";


    }

}
