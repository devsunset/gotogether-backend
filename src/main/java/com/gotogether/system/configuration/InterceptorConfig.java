package com.gotogether.system.configuration;


import com.gotogether.system.interceptor.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    LoggerInterceptor loggerInterceptor;

//        private static final List<String> INTERCEPTOR_URL_PATTERNS = Arrays.asList("/api/*");

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggerInterceptor);
//        registry.addInterceptor(new LoggerInterceptor()).addPathPatterns(INTERCEPTOR_URL_PATTERNS);
    }
}

