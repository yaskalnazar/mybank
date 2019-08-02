package org.itstep.controller.command.guest;

import org.apache.log4j.Logger;
import org.itstep.controller.JspPath;
import org.itstep.controller.command.Command;
import org.itstep.model.dto.UserDTO;
import org.itstep.model.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class RegistrationCommand implements Command {
    private RegistrationService registrationService = new RegistrationService();
    private final static Logger logger = Logger.getLogger(RegistrationCommand.class);


    //TODO regex check
    // @Override
    public String execute(HttpServletRequest request) {
        UserDTO userDTO = new UserDTO(
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("name"),
                request.getParameter("surname"),
                request.getParameter("patronymic"));

        if(userDTO.getEmail().equals("")){
            request.setAttribute("wrongEmail", "wrongEmail");
            return JspPath.REG_FORM;
        }
        request.setAttribute("email", userDTO.getEmail());


        if(userDTO.getName().equals("")){
            request.setAttribute("wrongName", "wrongName");
            return JspPath.REG_FORM;
        }
        request.setAttribute("name", userDTO.getName());


        if(userDTO.getSurname().equals("")){
            request.setAttribute("wrongSurname", "wrongSurname");
            return JspPath.REG_FORM;
        }
        request.setAttribute("surname", userDTO.getSurname());


        if(userDTO.getPatronymic().equals("")){
            request.setAttribute("wrongPatronymic", "wrongPatronymic");
            return JspPath.REG_FORM;
        }
        request.setAttribute("patronymic", userDTO.getPatronymic());

        if(userDTO.getPassword().equals("")){
            request.setAttribute("wrongPassword", "wrongPassword");
            return JspPath.REG_FORM;
        }
        request.setAttribute("password", userDTO.getPassword());


        registrationService.registerNewUser(userDTO);

        return JspPath.REG_FORM;
    }



}
