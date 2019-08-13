package ua.yaskal.controller.filters;

import org.apache.log4j.Logger;
import ua.yaskal.controller.configuration.AccessConfiguration;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.message.key.AccessDeniedException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * This filters implements authorization mechanism. If user has permission fro making request, than the chain will
 * be go further, else AccessDeniedException will be thrown.
 * If session does not contain user yet will be created new GUEST.
 *
 * @author Nazar Yaskal
 */
public class AccessFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AccessFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest Httprequest = (HttpServletRequest) request;
        HttpSession session = Httprequest.getSession();

        User user = (User) Optional.ofNullable(session.getAttribute("user"))
                .orElse(User.getBuilder().setUserRole(User.Role.GUEST).build());

        if (AccessConfiguration.isAccessAllowed(Httprequest.getRequestURI(), user.getRole())) {
            logger.debug("User " + user.getId() + " has access to " + Httprequest.getRequestURI());
            filterChain.doFilter(request, response);
        } else {
            logger.warn("User " + user.getId() + " tries to access " + Httprequest.getRequestURI() + " without permission");
            throw new AccessDeniedException();
        }

    }

    @Override
    public void destroy() {

    }
}
