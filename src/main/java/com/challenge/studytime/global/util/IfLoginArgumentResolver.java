package com.challenge.studytime.global.util;

import com.challenge.studytime.global.jwt.token.JwtAuthenticationToken;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collection;
import java.util.Iterator;

//컨트롤러 파라미터를 보고 값을 넣어준다. filter가 인증 정보를 담고 augument가 인증 정보를 가져와서 컨트롤러에 넣어준다.
public class IfLoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(IfLogin.class) != null
                && parameter.getParameterType() == LoginUserDto.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = null;
        try {
            authentication = SecurityContextHolder.getContext().getAuthentication();
        } catch (Exception ex) {
            return null;
        }
        if (authentication == null) {
            return null;
        }

        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        LoginUserDto loginUserDto = new LoginUserDto();

        Object principal = jwtAuthenticationToken.getPrincipal(); // LoginInfoDto
        if (principal == null)
            return null;

        LoginInfoDto loginInfoDto = (LoginInfoDto) principal;
        loginUserDto.setMemberId(loginInfoDto.getMemberId());

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            String role = grantedAuthority.getAuthority();
            loginUserDto.addRole(role);
        }

        return loginUserDto;
    }
}

