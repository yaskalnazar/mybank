package ua.yaskal.controller;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.*;
import ua.yaskal.controller.command.admin.AdminHomeCommand;
import ua.yaskal.controller.command.admin.AllDepositsCommand;
import ua.yaskal.controller.command.admin.AllUsersCommand;
import ua.yaskal.controller.command.guest.AllUsersAccountsCommand;
import ua.yaskal.controller.command.guest.LoginCommand;
import ua.yaskal.controller.command.guest.RegistrationCommand;
import ua.yaskal.controller.command.user.NewCreditRequestCommand;
import ua.yaskal.controller.command.user.DepositOpenCommand;
import ua.yaskal.controller.command.user.UserHomeCommand;

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

        commands.put("home", new HomeCommand());
        commands.put("user/home", new UserHomeCommand());
        commands.put("admin/home", new AdminHomeCommand());

        commands.put("user/logout", new LogOutCommand());
        commands.put("admin/logout",new LogOutCommand());

        commands.put("exception", new ExceptionCommand());

        commands.put("guest/login", new LoginCommand());
        commands.put("guest/registration", new RegistrationCommand());

        commands.put("admin/all_users" , new AllUsersCommand());
        commands.put("admin/account/all/deposits" , new AllDepositsCommand());

        commands.put("user/account/deposit/open", new DepositOpenCommand());
        commands.put("user/account/credit/open", new NewCreditRequestCommand());
        commands.put("user/account/all", new AllUsersAccountsCommand());


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
                (r) -> JspPath.ERROR404);
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
