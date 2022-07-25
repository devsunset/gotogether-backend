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


    /*
    // CORS Filter 적용 하려면 아래 설정 내용중 cors().and()를 삭제 필요
    http.cors().and().csrf().disable() -> http.csrf().disable()

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);
        source.registerCorsConfiguration("/api/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
    */

}
