package org.itstep.controller.Command;

import org.itstep.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command{

    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

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
            CommandUtility.setUserRole(request, User.ROLE.ADMIN, name);
            return "/WEB-INF/jsp/admin/adminbasis.jsp";
        } else if(name.equals("User")) {
            CommandUtility.setUserRole(request, User.ROLE.USER, name);
            return "/WEB-INF/jsp/user/userbasis.jsp";
        } else {
            CommandUtility.setUserRole(request, User.ROLE.UNKNOWN, name);
            return "/index.jsp";
        }


    }

}
