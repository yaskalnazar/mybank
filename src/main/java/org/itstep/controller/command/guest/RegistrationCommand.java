package org.itstep.controller.command.guest;

import org.apache.log4j.Logger;
import org.itstep.controller.JspPath;
import org.itstep.controller.command.Command;
import org.itstep.controller.util.ValidationUtil;
import org.itstep.model.dto.UserRegistrationDTO;
import org.itstep.model.exptions.NonUniqueEmailException;
import org.itstep.model.service.UserService;

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
            request.setAttribute("wrongEmail", "wrongEmail");
            return JspPath.REG_FORM;
        }
        request.setAttribute("email", userRegistrationDTO.getEmail());


        if(userRegistrationDTO.getName().equals("")){
            request.setAttribute("wrongName", "wrongName");
            return JspPath.REG_FORM;
        }
        request.setAttribute("name", userRegistrationDTO.getName());


        if(userRegistrationDTO.getSurname().equals("")){
            request.setAttribute("wrongSurname", "wrongSurname");
            return JspPath.REG_FORM;
        }
        request.setAttribute("surname", userRegistrationDTO.getSurname());


        if(userRegistrationDTO.getPatronymic().equals("")){
            request.setAttribute("wrongPatronymic", "wrongPatronymic");
            return JspPath.REG_FORM;
        }
        request.setAttribute("patronymic", userRegistrationDTO.getPatronymic());

        if(userRegistrationDTO.getPassword().equals("")){
            request.setAttribute("wrongPassword", "wrongPassword");
            return JspPath.REG_FORM;
        }
        request.setAttribute("password", userRegistrationDTO.getPassword());

        try {
            userService.addNewUser(userRegistrationDTO);
        } catch (NonUniqueEmailException e){
            request.setAttribute("wrongEmail", "email alredy exist");
            return JspPath.REG_FORM;
        }

        request.setAttribute("regSuccessfully", "You successfully registered");

        return JspPath.LOGIN_FORM;
    }



}
