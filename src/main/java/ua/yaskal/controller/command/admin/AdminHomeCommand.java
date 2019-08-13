package ua.yaskal.controller.command.admin;

import ua.yaskal.controller.JspPath;
import ua.yaskal.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

/**
 * This command used to get home page for ADMIN.
 *
 * @author Nazar Yaskal
 */
public class AdminHomeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return JspPath.ADMIN_HOME;
    }
}
