package ua.yaskal.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

public class LanguageFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String langParameter = request.getParameter("locale");
        if (Objects.isNull(langParameter) || langParameter.isEmpty() ) {
            langParameter = (String) httpRequest.getSession().getAttribute("locale");
        }
        httpRequest.getSession().setAttribute("locale", langParameter);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
