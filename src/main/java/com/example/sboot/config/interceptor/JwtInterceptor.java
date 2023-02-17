package com.example.sboot.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.example.sboot.common.Constants;
import com.example.sboot.entity.User;
import com.example.sboot.exception.ServiceException;
import com.example.sboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 验证token，防止用户直接跳过登录查询到用户数据库的数据
 * @author LeslieLu
 */

public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        //执行认证
        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        //获取token中的userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException j){
            throw new ServiceException(Constants.CODE_401,"token验证失败");
        }
        //根据token中的userId查询数据库
        User user = userService.getById(userId);
        if(user == null){
            throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        //用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);  //验证token
        }catch (JWTDecodeException e){
            throw new ServiceException(Constants.CODE_401,"token验证失败，请重新登录");
        }

        return true;
    }
}
