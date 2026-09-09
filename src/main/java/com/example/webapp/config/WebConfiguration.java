package com.example.webapp.config;
import org.springframework.context.annotation.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.servlet.config.annotation.*;
import jakarta.servlet.DispatcherType;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Bean public org.springframework.web.servlet.view.InternalResourceViewResolver jspViewResolver() {
        var resolver = new org.springframework.web.servlet.view.InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/"); resolver.setSuffix(".jsp");
        return resolver;
    }
    @Bean public FilterRegistrationBean<ApplicationFilter> applicationFilter() {
        var bean = new FilterRegistrationBean<>(new ApplicationFilter());
        bean.setName("application"); bean.addUrlPatterns("/*"); bean.setOrder(0);
        bean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE, DispatcherType.ERROR);
        return bean;
    }
}
