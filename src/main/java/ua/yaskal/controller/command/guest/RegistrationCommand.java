package ua.yaskal.controller.command.guest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;
import ua.yaskal.controller.util.ValidationUtil;
import ua.yaskal.model.dto.UserRegistrationDTO;
import ua.yaskal.model.exceptions.NonUniqueEmailException;
import ua.yaskal.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class RegistrationCommand implements Command {
    private final static Logger logger = Logger.getLogger(RegistrationCommand.class);
    private ValidationUtil validationUtil;
    private UserService userService;

    public RegistrationCommand(ValidationUtil validationUtil, UserService userService) {
        this.validationUtil = validationUtil;
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        if (!validationUtil.isContains(request, Arrays.asList("email", "password", "name", "surname", "patronymic"))) {
            return JspPath.REG_FORM;
        }

        if (isRequestValid(request)) {
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(
                    request.getParameter("email"),
                    DigestUtils.sha256Hex(request.getParameter("password")),
                    request.getParameter("name"),
                    request.getParameter("surname"),
                    request.getParameter("patronymic"));

            try {
                userService.addNewUser(userRegistrationDTO);
                logger.debug("User " + userRegistrationDTO.getEmail() + " registered successfully");
                request.setAttribute("regSuccessfully",  true);
                return JspPath.LOGIN_FORM;
            } catch (NonUniqueEmailException e) {
                logger.warn("Registration attempt with existing email " + userRegistrationDTO.getEmail());
                request.setAttribute("wrongEmail", "email.already.exist");
            }
        }
        return JspPath.REG_FORM;

    }

    private boolean isRequestValid(HttpServletRequest request) {
        boolean flag = true;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String patronymic = request.getParameter("patronymic");

        if (validationUtil.isParamValid(email, "email")) {
            request.setAttribute("email", email);
        } else {
            logger.warn("Registration attempt with incorrect email");
            request.setAttribute("wrongEmail", "wrong.email");
            flag = false;
        }

        if (!validationUtil.isParamValid(password, "password")) {
            logger.warn("Registration attempt with incorrect password");
            request.setAttribute("wrongPassword", "wrong.password");
            flag = false;
        }

        if (validationUtil.isParamValid(name, "name")) {
            request.setAttribute("name", name);
        } else {
            logger.warn("Registration attempt with incorrect name");
            request.setAttribute("wrongName", "wrong.name");
            flag = false;
        }

        if (validationUtil.isParamValid(surname, "surname")) {
            request.setAttribute("surname", surname);
        } else {
            logger.warn("Registration attempt with incorrect surname");
            request.setAttribute("wrongSurname", "wrong.surname");
            flag = false;
        }

        if (validationUtil.isParamValid(patronymic, "patronymic")) {
            request.setAttribute("patronymic", patronymic);
        } else {
            logger.warn("Registration attempt with incorrect patronymic");
            request.setAttribute("wrongPatronymic", "wrong.patronymic");
            flag = false;
        }
        return flag;
    }


    public void setValidationUtil(ValidationUtil validationUtil) {
        this.validationUtil = validationUtil;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
