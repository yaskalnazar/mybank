package org.itstep.controller;

import org.apache.log4j.Logger;
import org.itstep.controller.command.*;
import org.itstep.controller.command.guest.LoginCommand;
import org.itstep.controller.command.guest.LoginFormCommand;
import org.itstep.controller.command.guest.RegFormCommand;
import org.itstep.controller.command.guest.RegistrationCommand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(Servlet.class);
    private Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("home" , new HomeCommand());
        commands.put("exception" , new ExceptionCommand());
        commands.put("logout",
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
                new HomeCommand());
        System.out.println(command.getClass().getName());
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request,response);
    }
}
