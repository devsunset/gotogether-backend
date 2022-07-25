package com.gotogether.system.configuration;

import com.gotogether.system.filter.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FilterConfig{

    @Bean
    public CorsFilter corsFilterBean(){
        return new CorsFilter();
    }

    /*
    //##########################################################
    //[삭제 금지] 필요한 경우 주석 해제 후 사용//
    @Bean
    public FilterRegistrationBean<GlobalFilter> filterRegister() {
        FilterRegistrationBean<GlobalFilter> globalFilterBean = new FilterRegistrationBean<>(new GlobalFilter());
        return globalFilterBean;
    }

    @Bean
    public LoggingFilter loggingFilterBean(){
        return new LoggingFilter();
    }
    //##########################################################
     */

    @Bean
    public RequestServletXssFilter requestServletXssFilterBean(){
        return new RequestServletXssFilter();
    }

    @Bean
    public RequestBodyXssFIleter requestBodyXssFIleterBean(){
        return new RequestBodyXssFIleter();
    }
}
