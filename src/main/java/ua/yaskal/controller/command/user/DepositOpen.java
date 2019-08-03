package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.DepositDTO;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.service.DepositService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;

public class DepositOpen implements Command {
    private final static Logger logger = Logger.getLogger(DepositOpen.class);
    private DepositService depositService = new DepositService();
    private ValidationUtil validationUtil = new ValidationUtil();

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isСontain(request, Arrays.asList("depositAmount","depositRate", "monthsAmount"))){
            return JspPath.DEPOSIT_OPEN;
        }

        String depositAmount = request.getParameter("depositAmount");
        if(!validationUtil.isValid(depositAmount,"depositAmount")){
            logger.warn("Deposit open attempt with incorrect deposit amount"+depositAmount);
            request.setAttribute("wrongInput", "wrongDepositAmount");
            return JspPath.DEPOSIT_OPEN;
        }
        request.setAttribute("depositAmount", depositAmount);

        String depositRate = request.getParameter("depositRate");
        if(!validationUtil.isValid(depositRate, "depositRate")){
            logger.warn("Deposit open attempt with incorrect deposit rate"+depositRate);
            request.setAttribute("wrongInput", "wrongDepositRate");
            return JspPath.DEPOSIT_OPEN;
        }
        request.setAttribute("depositRate", depositRate);

        String monthsAmount = request.getParameter("monthsAmount");
        if(!validationUtil.isValid(monthsAmount, "monthsAmount")){
            logger.warn("Deposit open attempt with incorrect months amount"+monthsAmount);
            request.setAttribute("wrongInput", "wrongMonthsAmount");
            return JspPath.DEPOSIT_OPEN;
        }
        request.setAttribute("monthsAmount", monthsAmount);

        DepositDTO depositDTO = new DepositDTO(
                new BigDecimal(depositAmount),
                new BigDecimal(depositRate),
                Integer.parseInt(monthsAmount),
                (long) request.getSession().getAttribute("userId"));

        DepositAccount depositAccount;
        try {
            depositAccount = depositService.openNewDeposit(depositDTO);
        } catch (Exception e){
            logger.warn("Deposit open attempt with incorrect months amount"+monthsAmount);
            request.setAttribute("depositError", e);
            return JspPath.DEPOSIT_OPEN;
        }

        logger.debug("Deposit open successfully (id:"+depositAccount.getId()+")");
        request.setAttribute("depositSuccess", depositAccount);
        return JspPath.DEPOSIT_OPEN;
    }
}
