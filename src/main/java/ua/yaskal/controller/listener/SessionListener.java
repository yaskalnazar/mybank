package ua.yaskal.controller.listener;

import org.apache.log4j.Logger;
import ua.yaskal.controller.command.general.LogOutCommand;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashSet;


public class SessionListener implements HttpSessionListener {
    private final static Logger logger = Logger.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(60*15);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().getServletContext().removeAttribute(
                (String) httpSessionEvent.getSession().getAttribute("email"));

    }
}
