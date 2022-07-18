package com.gotogether.system.configuration;

import com.gotogether.system.filter.GlobalFilter;
import com.gotogether.system.filter.XssEscapeServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig{

    @Bean
    public FilterRegistrationBean<GlobalFilter> firstFilterRegister() {
        FilterRegistrationBean<GlobalFilter> registrationBean = new FilterRegistrationBean<>(new GlobalFilter());
        return registrationBean;
    }

    @Bean
    public XssEscapeServletFilter xssFilterBean(){
        return new XssEscapeServletFilter();
    }
}
