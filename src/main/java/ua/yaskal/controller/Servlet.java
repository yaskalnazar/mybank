package ua.yaskal.controller;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.*;
import ua.yaskal.controller.command.admin.AdminHome;
import ua.yaskal.controller.command.guest.LoginCommand;
import ua.yaskal.controller.command.guest.RegistrationCommand;
import ua.yaskal.controller.command.user.UserHome;

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
        logger.info("----------------------------------------------------------");
        logger.info("Starting project");
        logger.info("----------------------------------------------------------");

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());
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
        commands.put("guest/registration" , new RegistrationCommand());
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        path = path.replaceAll(".*/mybank/" , "");
        Command command = commands.getOrDefault(path ,
                (r) -> "/WEB-INF/jsp/errors/404error.jsp");
        System.out.println(command.getClass().getName());
        String page = command.execute(request);

        if (page.contains("redirect:")) {
            logger.trace("Redirecting to " + page);
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            logger.trace("Forwarding to " + page);
            request.getRequestDispatcher(page).forward(request,response);
        }
    }
}
