package com.gotogether.system.configuration;

import com.gotogether.system.filter.GlobalFilter;
import com.gotogether.system.filter.LoggingFilter;
import com.gotogether.system.filter.RequestBodyXssFIleter;
import com.gotogether.system.filter.RequestServletXssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
public class FilterConfig{

//    To-Do CorsFilter 처리
//    @Bean
//    public FilterRegistrationBean<CorsFilter> corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.setMaxAge(3600L);
//        source.registerCorsConfiguration("/*", config);
//        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//        bean.setOrder(0);
//        return bean;
//    }

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
