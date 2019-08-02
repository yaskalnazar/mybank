package org.itstep.controller.command.guest;


import org.apache.log4j.Logger;
import org.itstep.controller.command.Command;
import org.itstep.model.entity.User;
import org.itstep.model.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final static Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {


        UserDTO userDTO = new UserDTO();
        User user = User.getBuilder()
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword())
                .setUserRole(User.Role.USER)
                .build();

        if (userDTO.getEmail().equals("admin@a")){
            user.setRole(User.Role.ADMIN);
        }


        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userid", user.getId());
        logger.debug("User " + user.getRole());
        return "redirect:/mybank/home";


    }

}
