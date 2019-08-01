package org.itstep.controller.command.guest;

import org.itstep.controller.command.Command;
import org.itstep.model.entity.dto.UserDTO;
import org.itstep.model.service.RegistrationService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {

    private RegistrationService registrationService = new RegistrationService();

    @Override
    public String execute(HttpServletRequest request) {

        UserDTO userDTO = new UserDTO(request.getParameter("email"),request.getParameter("password"));
        registrationService.registerNewUser(userDTO);

        return null;
    }
}
