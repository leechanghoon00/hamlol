package org.example.hamlol.config;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends GenericFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        log.info("➡️ [요청] {} {}", request.getMethod(), request.getRequestURI());

        chain.doFilter(req, res);

        HttpServletResponse response = (HttpServletResponse) res;
        log.info("⬅️ [응답] {} {}", request.getRequestURI(), response.getStatus());
    }
}
