package com.gotogether.system.aop.log;


public interface LogTrace {
    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Throwable e);
}
