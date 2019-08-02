package org.itstep.controller.command.guest;


import org.apache.log4j.Logger;
import org.itstep.controller.JspPath;
import org.itstep.controller.command.Command;
import org.itstep.controller.util.ValidationUtil;
import org.itstep.model.dto.UserLoginDTO;
import org.itstep.model.entity.User;
import org.itstep.model.service.UserService;

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

        User user = userService.loginUser(userLoginDTO);





        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userid", user.getId());
        logger.debug("User " + user.getRole());
        return "redirect:/mybank/home";


    }

}
