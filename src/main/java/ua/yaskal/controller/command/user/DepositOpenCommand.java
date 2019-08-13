package ua.yaskal.controller.command.user;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.DepositDTO;
import ua.yaskal.model.entity.DepositAccount;
import ua.yaskal.model.service.DepositService;
import ua.yaskal.model.service.ScheduledService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

/**
 * This command used for getting deposit open to USER and process answer.
 * Required params: depositAmount, depositRate, monthsAmount if deposit has been requested;
 *
 * @author Nazar Yaskal
 */
public class DepositOpenCommand implements Command {
    private final static Logger logger = Logger.getLogger(DepositOpenCommand.class);
    private ValidationUtil validationUtil;
    private DepositService depositService;
    private ScheduledService scheduledService;

    public DepositOpenCommand(ValidationUtil validationUtil, DepositService depositService, ScheduledService scheduledService) {
        this.validationUtil = validationUtil;
        this.depositService = depositService;
        this.scheduledService = scheduledService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isContains(request, Arrays.asList("depositAmount","depositRate", "monthsAmount"))){
            return JspPath.USER_DEPOSIT_OPEN;
        }

        String depositAmount = request.getParameter("depositAmount");
        if(!validationUtil.isParamValid(depositAmount,"depositAmount")){
            logger.warn("Deposit open attempt with incorrect deposit amount"+depositAmount);
            request.setAttribute("wrongInput", "wrongDepositAmount");
            return JspPath.USER_DEPOSIT_OPEN;
        }

        String depositRate = request.getParameter("depositRate");
        if(!validationUtil.isParamValid(depositRate, "depositRate")){
            logger.warn("Deposit open attempt with incorrect deposit rate"+depositRate);
            request.setAttribute("wrongInput", "wrongDepositRate");
            return JspPath.USER_DEPOSIT_OPEN;
        }

        String monthsAmount = request.getParameter("monthsAmount");
        if(!validationUtil.isParamValid(monthsAmount, "monthsAmount")){
            logger.warn("Deposit open attempt with incorrect months amount"+monthsAmount);
            request.setAttribute("wrongInput", "wrongMonthsAmount");
            return JspPath.USER_DEPOSIT_OPEN;
        }

        DepositDTO depositDTO = new DepositDTO(
                new BigDecimal(depositAmount),
                new BigDecimal(depositRate),
                Integer.parseInt(monthsAmount),
                (long) request.getSession().getAttribute("userId"));

        DepositAccount depositAccount;
        try {
            depositAccount = depositService.openNewDeposit(depositDTO);
            scheduledService.scheduleAccounts(Collections.singletonList(depositAccount));

        } catch (Exception e){
            logger.warn("Deposit open error"+e);
            request.setAttribute("depositError", e);
            return JspPath.USER_DEPOSIT_OPEN;
        }

        logger.debug("Deposit open successfully (id:"+depositAccount.getId()+")");
        request.setAttribute("depositSuccess", depositAccount);
        return JspPath.USER_DEPOSIT_OPEN;
    }

    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setDepositService(DepositService depositService) {
        this.depositService = depositService;
    }

    public void setScheduledService(ScheduledService scheduledService) {
        this.scheduledService = scheduledService;
    }
}
