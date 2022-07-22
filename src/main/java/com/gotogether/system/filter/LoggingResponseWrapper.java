package com.gotogether.system.filter;

import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletResponse;

public class LoggingResponseWrapper extends ContentCachingResponseWrapper {
    public LoggingResponseWrapper(HttpServletResponse response) {
        super(response);
    }
}