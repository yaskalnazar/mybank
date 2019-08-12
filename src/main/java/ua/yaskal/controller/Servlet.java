package ua.yaskal.controller;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.command.admin.*;
import ua.yaskal.controller.command.general.HomeCommand;
import ua.yaskal.controller.command.general.LogOutCommand;
import ua.yaskal.controller.command.guest.LoginCommand;
import ua.yaskal.controller.command.guest.RegistrationCommand;
import ua.yaskal.controller.command.user.*;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dao.DAOFactory;
import ua.yaskal.model.service.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;


public class Servlet extends HttpServlet {
    private final static Logger logger = Logger.getLogger(Servlet.class);
    private Map<String, Command> commands = new HashMap<>();
    private DAOFactory daoFactory;
    private ValidationUtil validationUtil;
    private PaymentService paymentService;
    private AccountService accountService;
    private TransactionService transactionService;
    private CreditService creditService;
    private DepositService depositService;
    private UserService userService;
    private CreditRequestService creditRequestService;
    private ScheduledService scheduledService;


    public void init(ServletConfig servletConfig) {
        logger.info("----------------------------------------------------------");
        logger.info("Starting project");
        logger.info("----------------------------------------------------------");

        daoFactory = DAOFactory.getInstance();
        validationUtil = new ValidationUtil();
        paymentService = new PaymentService(daoFactory);
        accountService = new AccountService(daoFactory);
        transactionService = new TransactionService(daoFactory);
        creditService = new CreditService(daoFactory);
        depositService = new DepositService(daoFactory);
        userService = new UserService(daoFactory);
        creditRequestService = new CreditRequestService(daoFactory);

        ScheduledThreadPoolExecutor scheduledExecutorService = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
        scheduledExecutorService.setRemoveOnCancelPolicy(true);
        scheduledExecutorService.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        scheduledExecutorService.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
        scheduledService = new ScheduledService(scheduledExecutorService, daoFactory);

        servletConfig.getServletContext()
                .setAttribute("loggedUsers", new HashSet<String>());

        commands.put("home", new HomeCommand());
        commands.put("user/home", new UserHomeCommand());
        commands.put("admin/home", new AdminHomeCommand());

        commands.put("user/logout", new LogOutCommand());
        commands.put("admin/logout", new LogOutCommand());


        commands.put("guest/login",
                new LoginCommand(validationUtil, userService));
        commands.put("guest/registration",
                new RegistrationCommand(validationUtil, userService));

        commands.put("admin/all_users",
                new AllUsersCommand(validationUtil, userService));
        commands.put("admin/account/all/deposits",
                new AllDepositsCommand(validationUtil, depositService));
        commands.put("admin/account/all/credits",
                new AllCreditsCommand(validationUtil, creditService));
        commands.put("admin/credit_request",
                new CreditRequestCommand(validationUtil, creditRequestService, userService, creditService, scheduledService));
        commands.put("admin/credit_request/all",
                new GetCreditRequestsCommand(validationUtil, creditRequestService));
        commands.put("admin/user_page",
                new GetUserPageCommand(validationUtil, userService, creditService, depositService));
        commands.put("admin/account/credit_page",
                new AdminCreditPageCommand(validationUtil, creditService, transactionService, accountService));
        commands.put("admin/account/deposit_page",
                new AdminDepositPageCommand(validationUtil, depositService, transactionService, accountService));


        commands.put("user/account/all",
                new AllUsersAccountsCommand(depositService, creditService));
        commands.put("user/account/deposit/open",
                new DepositOpenCommand(validationUtil, depositService, scheduledService));
        commands.put("user/account/credit/open",
                new NewCreditRequestCommand(validationUtil, creditRequestService, creditService));
        commands.put("user/account/replenish",
                new ReplenishAccountCommand(validationUtil, accountService, transactionService));
        commands.put("user/account/make_transaction",
                new MakeTransactionCommand(validationUtil, accountService, transactionService));
        commands.put("user/account/credit_page",
                new UserCreditPageCommand(validationUtil, creditService, transactionService, accountService));
        commands.put("user/account/deposit_page",
                new UserDepositPageCommand(validationUtil, depositService, transactionService));
        commands.put("user/payment/make_new",
                new MakePaymentCommand(validationUtil, paymentService, accountService));
        commands.put("user/payment/all",
                new AllUsersPayment(validationUtil, paymentService, accountService, transactionService));
        commands.put("user/credit_request/close",
                new DeleteCreditRequestCommand(validationUtil, creditRequestService));
        commands.put("user/account/credit/pay_accrued_interest",
                new PayAccruedInterestCommand(validationUtil, accountService, transactionService, creditService));


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
