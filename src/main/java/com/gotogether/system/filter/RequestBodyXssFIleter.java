package com.gotogether.system.filter;

import com.gotogether.system.constants.Constants;
import com.gotogether.system.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Order(3)
public class RequestBodyXssFIleter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        RequestBodyXssWrapper requestWrapper = null;
        //xss h2-console skip
        if(((HttpServletRequest) req).getRequestURL().indexOf(Constants.XSS_H2CONSOLE_SKIP) > -1){
            chain.doFilter(request, response);
        }else{
            try {requestWrapper = new RequestBodyXssWrapper(request);}
            catch (Exception e) {
                log.error("ERROR",e);
            }
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
