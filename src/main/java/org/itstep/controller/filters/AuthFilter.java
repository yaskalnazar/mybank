package org.itstep.controller.filters;

import org.apache.log4j.Logger;
import org.itstep.controller.configuration.AccessConfiquration;
import org.itstep.model.entity.User;
import org.itstep.model.service.RegistrationService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

import static org.itstep.controller.JspPath.ACCESS_DENIED;

public class AuthFilter implements Filter {
    private final static Logger logger = Logger.getLogger(RegistrationService.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        ServletContext context = request.getServletContext();

        User user = (User) Optional.ofNullable(session.getAttribute("user"))
                .orElse(User.getBuilder().setUserRole(User.Role.GUEST).build());
        logger.debug("URI " + req.getRequestURI());
        if (AccessConfiquration.isAccessAllowed(req.getRequestURI(),user.getRole()) ){
            logger.debug("User " + user.getId() + "has permission");
            filterChain.doFilter(request, response);
        } else {
            logger.debug("User " + user.getId() + "does not have permission");
            resp.sendRedirect("/mybank/home");
        }
      /*  filterChain.doFilter(request, response);*/

    }

    @Override
    public void destroy() {

    }
}
