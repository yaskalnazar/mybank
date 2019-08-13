package ua.yaskal.controller.listener;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * This listener used for setting MaxInactiveInterval to new sessions
 * and removing users from signedOut when session closed.
 *
 * @author Nazar Yaskal
 */
public class SessionListener implements HttpSessionListener {
    private final static Logger logger = Logger.getLogger(SessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setMaxInactiveInterval(60 * 15);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().getServletContext().removeAttribute(
                (String) httpSessionEvent.getSession().getAttribute("email"));

    }
}
