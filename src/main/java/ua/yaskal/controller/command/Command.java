package ua.yaskal.controller.command;

import ua.yaskal.model.dao.DAOFactory;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request);
}
