package com.gotogether.system.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
@Component
public class GlobalFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("========== GlobalFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("========== GlobalFilter start");
        filterChain.doFilter(servletRequest, servletResponse);
        log.info("========== GlobalFilter end");
    }

    @Override
    public void destroy() {
        log.info("========== GlobalFilter destroy");
    }
}
