package ua.yaskal.controller.command.admin;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.service.CreditRequestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class CreditRequestCommand implements Command {
    private final static Logger logger = Logger.getLogger(CreditRequestCommand.class);
    private CreditRequestService creditRequestService = new CreditRequestService();
    private ValidationUtil validationUtil = new ValidationUtil();


    @Override
    public String execute(HttpServletRequest request) {

        if (!validationUtil.isСontain(request, Arrays.asList("id"))) {
            logger.warn("No id in request");
            request.setAttribute("error", "page.message.no.id");
            return JspPath.ALL_CREDIT_REQUESTS;
        }

        request.setAttribute("creditRequest",
                creditRequestService.getById(Long.parseLong(request.getParameter("id"))));
        
        return JspPath.ADMIN_CREDIT_REQUEST;

    }
}
