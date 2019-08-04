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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewCreditRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(NewCreditRequestCommand.class);
    private ValidationUtil validationUtil = new ValidationUtil();
    private CreditRequestService creditRequestService = new CreditRequestService();
    private CreditService creditService = new CreditService();

    //TODO stream and refactor
    @Override
    public String execute(HttpServletRequest request) {
        long userId = (long) request.getSession().getAttribute("userId");

        List<CreditAccount> creditAccounts= creditService.getAllByOwnerId(userId);
        List<CreditAccount> activeCreditAccounts= new ArrayList<>();

        for (CreditAccount i: creditAccounts){
            if (i.getAccountStatus().equals(Account.AccountStatus.ACTIVE)){
                activeCreditAccounts.add(i);
            }
        }

        if(!activeCreditAccounts.isEmpty()){
            logger.warn("Credit open attempt with activeCreditAccounts userId("+userId+")");
            request.setAttribute("activeCreditAccounts", activeCreditAccounts);
            return JspPath.CREDIT_OPEN;
        }

        List<CreditRequest> creditRequests = creditRequestService.getAllByApplicantId(userId);
        List<CreditRequest> activeCreditRequests = new ArrayList<>();

        for (CreditRequest i: creditRequests){
            if (i.getCreditRequestStatus().equals(CreditRequest.CreditRequestStatus.PENDING)){
                activeCreditRequests.add(i);
            }
        }

        if(!activeCreditRequests.isEmpty()){
            logger.warn("Credit open attempt with activeCreditRequests userId("+userId+")");
            request.setAttribute("activeCreditRequests", activeCreditRequests);
            return JspPath.CREDIT_OPEN;
        }



        if (!validationUtil.isСontain(request, Arrays.asList("creditLimit","creditRate"))){
            return JspPath.CREDIT_OPEN;
        }

        String creditLimit = request.getParameter("creditLimit");
        if(!validationUtil.isValid(creditLimit,"creditLimit")){
            logger.warn("Credit open attempt with incorrect credit limit"+creditLimit);
            request.setAttribute("wrongInput", "wrongCreditLimit");
            return JspPath.CREDIT_OPEN;
        }

        String creditRate = request.getParameter("creditRate");
        if(!validationUtil.isValid(creditRate,"creditRate")){
            logger.warn("Credit open attempt with incorrect credit rate"+creditRate);
            request.setAttribute("wrongInput", "wrongCreditRate");
            return JspPath.CREDIT_OPEN;
        }

        CreditRequestDTO creditRequestDTO = new CreditRequestDTO(
                (long) request.getSession().getAttribute("userId"),
                new BigDecimal(creditRate),
                new BigDecimal(creditLimit),
                LocalDate.now()
        );


        CreditRequest creditRequest;
        try {
            creditRequest = creditRequestService.createNew(creditRequestDTO);
        } catch (Exception e){
            e.printStackTrace();
            logger.warn("Credit request error "+e.getStackTrace());
            request.setAttribute("creditRequestError", e);
            return JspPath.CREDIT_OPEN;
        }

        logger.debug("Credit request successfully (id:"+creditRequest.getId()+")");
        request.setAttribute("creditRequestSuccess", creditRequest);
        return JspPath.CREDIT_OPEN;
    }
}
