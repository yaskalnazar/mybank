package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.CreditRequestDTO;
import ua.yaskal.model.entity.CreditRequest;
import ua.yaskal.model.service.CreditRequestService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

public class NewCreditRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(NewCreditRequestCommand.class);
    private ValidationUtil validationUtil = new ValidationUtil();
    private CreditRequestService creditRequestService = new CreditRequestService();

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isСontain(request, Arrays.asList("creditLimit","creditRate"))){
            return JspPath.CREDIT_OPEN;
        }

        String creditLimit = request.getParameter("creditLimit");
        if(!validationUtil.isValid(creditLimit,"creditLimit")){
            logger.warn("Credit open attempt with incorrect credit limit"+creditLimit);
            request.setAttribute("wrongInput", "wrongCreditLimit");
            return JspPath.DEPOSIT_OPEN;
        }

        String creditRate = request.getParameter("creditRate");
        if(!validationUtil.isValid(creditRate,"creditRate")){
            logger.warn("Credit open attempt with incorrect credit rate"+creditRate);
            request.setAttribute("wrongInput", "wrongCreditRate");
            return JspPath.DEPOSIT_OPEN;
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
