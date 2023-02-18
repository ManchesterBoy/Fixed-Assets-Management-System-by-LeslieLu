package com.example.sboot.config;

import com.example.sboot.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * token检测用户是否合法登录
 * @author LeslieLu
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")     //拦截所有请求，通过判断token是否合法来决定是否需要登录
                //防止用户直接跳过登录访问到对应的数据
//                .excludePathPatterns("/user/login","/user/register","/**/export","/**/import","/file/**");
                .excludePathPatterns("/user/login","/user/register","/**/export","/**/import","/file/{fileUUid}");
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }

}
