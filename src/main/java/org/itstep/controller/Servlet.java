package org.itstep.controller;

import org.apache.log4j.Logger;
import org.itstep.controller.command.*;
import org.itstep.controller.command.admin.AdminHome;
import org.itstep.controller.command.guest.LoginCommand;
import org.itstep.controller.command.guest.LoginFormCommand;
import org.itstep.controller.command.guest.RegFormCommand;
import org.itstep.controller.command.guest.RegistrationCommand;
import org.itstep.controller.command.user.UserHome;
import org.itstep.model.dao.jdbc.JDBCDaoFactory;
import org.itstep.model.dao.jdbc.JDBCUserDao;
import org.itstep.model.entity.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(Servlet.class);
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){


        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
        /*User user = User.getBuilder()
                .setUserRole(User.Role.ADMIN)
                .setEmail("admin@a")
                .setPassword("admin")
                .setName("name")
                .setPatronymic("pan")
                .setSurname("sfsf")
                .build();
        new JDBCDaoFactory().createUserDao().addNew(user);*/
        commands.put("home" , new HomeCommand());
        commands.put("user/home" , new UserHome());
        commands.put("admin/home" , new AdminHome());
        commands.put("exception" , new ExceptionCommand());
        commands.put("user/logout",
                new LogOutCommand());
        commands.put("admin/logout",
                new LogOutCommand());
        commands.put("guest/login",
                new LoginCommand());
        commands.put("guest/reg_form" , new RegFormCommand());
        commands.put("guest/login_form" , new LoginFormCommand());
        commands.put("guest/registration" , new RegistrationCommand());

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
        //response.getWriter().print("Hello from servlet");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        logger.debug("Servlet " + path);
        path = path.replaceAll(".*/mybank/" , "");
        Command command = commands.getOrDefault(path ,
                (r) -> "/WEB-INF/jsp/errors/404error.jsp");
        System.out.println(command.getClass().getName());
        String page = command.execute(request);

        if (page.contains("redirect:")) {
            logger.debug("Redirecting to " + page);
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            logger.debug("Forwarding to " + page);
            request.getRequestDispatcher(page).forward(request,response);
        }
    }
}
