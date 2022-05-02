package com.mycompany.helloworld.springboot;

import org.springframework.stereotype.Component;

import javax.servlet.*;

// @Component
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("MyFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        System.out.println("MyFilter doFilter");
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter destroy");

    }
}
