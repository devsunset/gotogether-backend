package com.gotogether.system.filter;

import com.gotogether.system.constants.Constants;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestBodyXSSFIleter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        RequestWrapper requestWrapper = null;
        //xss h2-console skip
        if(((HttpServletRequest) req).getRequestURL().indexOf(Constants.XSS_H2CONSOLE_SKIP) > -1){
            chain.doFilter(request, response);
        }else{
            try {requestWrapper = new RequestWrapper(request);}
            catch (Exception e) {
                e.printStackTrace();
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
