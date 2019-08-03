package ua.yaskal.controller.command.guest;

import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.UserRegistrationDTO;
import ua.yaskal.model.exptions.NonUniqueEmailException;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class RegistrationCommand implements Command {
    private final static Logger logger = Logger.getLogger(RegistrationCommand.class);
    private UserService userService = new UserService();
    private ValidationUtil validationUtil = new ValidationUtil();



    //TODO regex check
    // @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isСontain(request, Arrays.asList("email", "password","name","surname","patronymic"))){
            return JspPath.REG_FORM;
        }


        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("surname"),
                request.getParameter("patronymic"));

        if(userRegistrationDTO.getEmail().equals("")){
            logger.warn("Registration attempt with incorrect email");
            request.setAttribute("wrongEmail", "wrongEmail");
            return JspPath.REG_FORM;
        }
        request.setAttribute("email", userRegistrationDTO.getEmail());


        if(userRegistrationDTO.getName().equals("")){
            logger.warn("Registration attempt with incorrect name");
            request.setAttribute("wrongName", "wrongName");
            return JspPath.REG_FORM;
        }
        request.setAttribute("name", userRegistrationDTO.getName());


        if(userRegistrationDTO.getSurname().equals("")){
            logger.warn("Registration attempt with incorrect surname");
            request.setAttribute("wrongSurname", "wrongSurname");
            return JspPath.REG_FORM;
        }
        request.setAttribute("surname", userRegistrationDTO.getSurname());


        if(userRegistrationDTO.getPatronymic().equals("")){
            logger.warn("Registration attempt with incorrect patronymic");
            request.setAttribute("wrongPatronymic", "wrongPatronymic");
            return JspPath.REG_FORM;
        }
        request.setAttribute("patronymic", userRegistrationDTO.getPatronymic());

        if(userRegistrationDTO.getPassword().equals("")){
            logger.warn("Registration attempt with incorrect password");
            request.setAttribute("wrongPassword", "wrongPassword");
            return JspPath.REG_FORM;
        }
        request.setAttribute("password", userRegistrationDTO.getPassword());

        try {
            userService.addNewUser(userRegistrationDTO);
        } catch (NonUniqueEmailException e){
            logger.warn("Registration attempt with existing email "+userRegistrationDTO.getEmail());
            request.setAttribute("wrongEmail", "email already exist");
            return JspPath.REG_FORM;
        }

        logger.debug("User "+userRegistrationDTO.getEmail()+" registered successfully");
        request.setAttribute("regSuccessfully", "You successfully registered");
        return JspPath.LOGIN_FORM;
    }



}
