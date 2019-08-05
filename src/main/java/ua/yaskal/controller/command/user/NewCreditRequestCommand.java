package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.CreditRequestDTO;
import ua.yaskal.model.entity.Account;
import ua.yaskal.model.entity.CreditAccount;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.service.CreditRequestService;
import ua.yaskal.model.service.CreditService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NewCreditRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(NewCreditRequestCommand.class);
    private ValidationUtil validationUtil = new ValidationUtil();
    private CreditRequestService creditRequestService = new CreditRequestService();
    private CreditService creditService = new CreditService();

    @Override
    public String execute(HttpServletRequest request) {

        if(!isUserCanRequestCredit(request)){
            return JspPath.CREDIT_OPEN;
        }

        if (!validationUtil.isContains(request, Arrays.asList("creditLimit","creditRate"))){
            return JspPath.CREDIT_OPEN;
        }

        if (!validationUtil.isRequestValid(request, Arrays.asList("creditLimit","creditRate"))){
            logger.warn("Credit open attempt with incorrect input"+request);
            request.setAttribute("wrongInput", "wrongInput");
            return JspPath.CREDIT_OPEN;
        }

        CreditRequestDTO creditRequestDTO = new CreditRequestDTO(
                (long) request.getSession().getAttribute("userId"),
                new BigDecimal(request.getParameter("creditRate")),
                new BigDecimal(request.getParameter("creditLimit")),
                LocalDate.now()
        );

        CreditRequest creditRequest = creditRequestService.createNew(creditRequestDTO);

        logger.debug("Credit request successfully (id:"+creditRequest.getId()+")");
        request.setAttribute("creditRequestSuccess", creditRequest);
        return JspPath.CREDIT_OPEN;
    }

    public boolean isUserCanRequestCredit(HttpServletRequest request){
        long userId = (long) request.getSession().getAttribute("userId");

        return (isUserDontHaveActiveCredit(request, userId)
                && isUserDontHaveActiveRequests(request, userId));
    }

    public boolean isUserDontHaveActiveCredit(HttpServletRequest request, long userId){
        List<CreditAccount> activeCreditAccounts = creditService.getAllByOwnerId(userId).stream()
                .filter((s) -> s.getAccountStatus().equals(Account.AccountStatus.ACTIVE))
                .collect(Collectors.toList());

        if(!activeCreditAccounts.isEmpty()){
            logger.warn("Credit open attempt with activeCreditAccounts userId("+userId+")");
            request.setAttribute("activeCreditAccounts", activeCreditAccounts);
            return false;
        }

        return true;
    }

    public boolean isUserDontHaveActiveRequests(HttpServletRequest request, long userId){
        List<CreditRequest> activeCreditRequests = creditRequestService.getAllByApplicantId(userId).stream()
                .filter((s) -> s.getCreditRequestStatus().equals(CreditRequest.CreditRequestStatus.PENDING))
                .collect(Collectors.toList());


        if(!activeCreditRequests.isEmpty()){
            logger.warn("Credit open attempt with activeCreditRequests userId("+userId+")");
            for(CreditRequest i:activeCreditRequests)
                logger.warn(i.getId()+" fuck");
            request.setAttribute("activeCreditRequests", activeCreditRequests);
            return false;
        }
        return  true;
    }
}
