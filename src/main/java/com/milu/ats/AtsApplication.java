package com.milu.ats;

import com.milu.ats.aspect.GlobalInterceptor;
import com.milu.ats.aspect.ResolveParamInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author max.chen
 * @class
 */
@EnableJpaAuditing
@SpringBootApplication
public class AtsApplication implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(AtsApplication.class, args);
    }

    @Bean
    public GlobalInterceptor getGlobalInterceptor(){
        return new GlobalInterceptor();
    }
    @Bean
    public ResolveParamInterceptor getResolveParamInterceptor(){
        return new ResolveParamInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getGlobalInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(getResolveParamInterceptor());
    }
}
