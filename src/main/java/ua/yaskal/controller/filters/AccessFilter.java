package ua.yaskal.controller.filters;

import org.apache.log4j.Logger;
import ua.yaskal.controller.configuration.AccessConfiquration;
import ua.yaskal.model.entity.User;
import ua.yaskal.model.exceptions.AccessDeniedException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AccessFilter implements Filter {
    private final static Logger logger = Logger.getLogger(AccessFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest Httprequest = (HttpServletRequest) request;
        HttpSession session = Httprequest.getSession();

        User user = (User) Optional.ofNullable(session.getAttribute("user"))
                .orElse(User.getBuilder().setUserRole(User.Role.GUEST).build());

        if (AccessConfiquration.isAccessAllowed(Httprequest.getRequestURI(),user.getRole()) ){
            logger.debug("User " + user.getId() + " has access to "+ Httprequest.getRequestURI());
            filterChain.doFilter(request, response);
        } else {
            logger.warn("User " + user.getId() + " tries to access " + Httprequest.getRequestURI() +" without permission");
            throw new AccessDeniedException();
        }

    }

    @Override
    public void destroy() {

    }
}
