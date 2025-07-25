package com.zerobase.minimart.config;

import com.zerobase.minimart.exception.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String msg = "로그인에 실패하였습니다.";

        if (exception instanceof InternalAuthenticationServiceException) {
            Throwable cause = exception.getCause();

            if (cause instanceof CustomException customEx) {
                msg = customEx.getMessage();
            } else if (cause instanceof UsernameNotFoundException) {
                msg = cause.getMessage();
            } else {
                msg = exception.getMessage();
            }

        } else if (exception instanceof BadCredentialsException) {
            msg = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if ("User is disabled".equalsIgnoreCase(exception.getMessage())) {
            msg = "이메일 인증을 완료해야 로그인할 수 있습니다.";
        }


        setUseForward(true);
        // ✅ 실패 후 index.html로 리다이렉트
        setDefaultFailureUrl("/?error=true");

        // 메시지를 세션에 저장 (Thymeleaf에서 보여줄 수 있게)
        request.getSession().setAttribute("errorMessage", msg);
        response.sendRedirect("/");
        super.onAuthenticationFailure(request, response, exception);
    }
}