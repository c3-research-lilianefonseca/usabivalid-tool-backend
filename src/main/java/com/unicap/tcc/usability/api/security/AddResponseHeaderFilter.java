package com.unicap.tcc.usability.api.security;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/*")
@Component
public class AddResponseHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        httpServletResponse.setHeader (HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD, "POST, PUT, GET, OPTIONS, DELETE");
        httpServletResponse.setHeader (HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
