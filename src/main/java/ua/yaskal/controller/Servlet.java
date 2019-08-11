package ua.yaskal.controller;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.*;
import ua.yaskal.controller.command.admin.*;
import ua.yaskal.controller.command.general.HomeCommand;
import ua.yaskal.controller.command.general.LogOutCommand;
import ua.yaskal.controller.command.guest.*;
import ua.yaskal.controller.command.user.*;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.service.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

//TODO ioc&di
public class Servlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(Servlet.class);
    private DAOFactory daoFactory;
    private ValidationUtil validationUtil;
    private PaymentService paymentService;
    private AccountService accountService;
    private TransactionService transactionService;
    private CreditService creditService;
    private DepositService depositService;
    private UserService userService;
    private CreditRequestService creditRequestService;
    private Map<String, Command> commands = new HashMap<>();
    private ScheduledThreadPoolExecutor scheduledExecutorService = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);


    public void init(ServletConfig servletConfig) {
        logger.info("----------------------------------------------------------");
        logger.info("Starting project");
        logger.info("----------------------------------------------------------");

        daoFactory = DAOFactory.getInstance();
        validationUtil = new ValidationUtil();
        paymentService = new PaymentService(daoFactory);
        accountService = new AccountService();
        transactionService = new TransactionService();
        creditService = new CreditService();
        depositService = new DepositService();
        userService = new UserService();
        creditRequestService = new CreditRequestService();


        scheduledExecutorService.setRemoveOnCancelPolicy(true);
        scheduledExecutorService.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        scheduledExecutorService.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        new ScheduledService(scheduledExecutorService, DAOFactory.getInstance());

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("home", new HomeCommand());
        commands.put("user/home", new UserHomeCommand());
        commands.put("admin/home", new AdminHomeCommand());

        commands.put("user/logout", new LogOutCommand());
        commands.put("admin/logout", new LogOutCommand());


        commands.put("guest/login", new LoginCommand(validationUtil, userService));
        commands.put("guest/registration", new RegistrationCommand(validationUtil, userService));

        commands.put("admin/all_users", new AllUsersCommand(userService));
        commands.put("admin/account/all/deposits", new AllDepositsCommand(depositService));
        commands.put("admin/account/all/credits", new AllCreditsCommand(creditService));
        commands.put("admin/credit_request", new CreditRequestCommand(validationUtil, creditRequestService, userService, creditService));
        commands.put("admin/credit_request/all", new GetCreditRequestsCommand(validationUtil, creditRequestService));
        commands.put("admin/user_page", new GetUserPageCommand(validationUtil, userService, creditService, depositService));


        commands.put("user/account/all", new AllUsersAccountsCommand(depositService, creditService));
        commands.put("user/account/deposit/open", new DepositOpenCommand(validationUtil, depositService));
        commands.put("user/account/credit/open", new NewCreditRequestCommand(validationUtil, creditRequestService, creditService));
        commands.put("user/account/replenish", new ReplenishAccountCommand(validationUtil, accountService, transactionService));
        commands.put("user/account/make_transaction", new MakeTransactionCommand(validationUtil, accountService, transactionService));
        commands.put("user/account/credit_page", new UserCreditPageCommand(validationUtil, creditService, transactionService));
        commands.put("user/account/deposit_page", new UserDepositPageCommand(validationUtil, depositService, transactionService));
        commands.put("user/payment/make_new", new MakePaymentCommand(validationUtil, paymentService, accountService));
        commands.put("user/payment/all", new AllUsersPayment(validationUtil, paymentService, accountService, transactionService));


        // new ScheduledService(new ScheduledThreadPoolExecutor(50), DaoFactory.getInstance());


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
        path = path.replaceAll(".*/mybank/", "");
        Command command = commands.getOrDefault(path,
                (r) -> JspPath.ERROR404);
        System.out.println(command.getClass().getName());
        String page = command.execute(request);

        if (page.contains("redirect:")) {
            logger.trace("Redirecting to " + page);
            response.sendRedirect(page.replace("redirect:", ""));
        } else {
            logger.trace("Forwarding to " + page);
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
