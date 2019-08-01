package org.itstep.controller.command.guest;


import org.apache.log4j.Logger;
import org.itstep.controller.command.Command;
import org.itstep.controller.command.HomeCommand;
import org.itstep.model.entity.User;
import org.itstep.model.entity.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private final static Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
       /* String name = request.getParameter("email");
        String pass = request.getParameter("password");

        if( name == null || name.equals("") || pass == null || pass.equals("")  ){
            //System.out.println("Not");
            return "/login.jsp";
        }
        System.out.println(name + " " + pass);
        //System.out.println("Yes!");
//todo: check login with DB

        if(CommandUtility.checkUserIsLogged(request, name)){
            return "/WEB-INF/jsp/error.jsp";
        }

        if (name.equals("Admin")){
            CommandUtility.setUserRole(request, User.Role.ADMIN, name);
            return "/WEB-INF/jsp/admin/adminHome.jsp";
        } else if(name.equals("User")) {
            CommandUtility.setUserRole(request, User.ROLE.USER, name);
            return "/WEB-INF/jsp/user/userHome.jsp";
        } else {
            CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, name);
            return "/index.jsp";
        }*/
        UserDTO userDTO = new UserDTO(
                request.getParameter("email"),request.getParameter("password"));

        User user = User.getBuilder()
                .setEmail(userDTO.getEmail())
                .setPassword(userDTO.getPassword())
                .setUserRole(User.Role.USER)
                .build();


        request.getSession().setAttribute("user", user);
        request.getSession().setAttribute("userid", user.getId());
        logger.debug("User " + user.getRole());
        return "redirect:/mybank/home";


    }

}
