package org.mh.studyspringbootsecurity.conf;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().contains("/h2-console") ||
            request.getRequestURI().contains("/swagger") ||
            request.getRequestURI().contains("/v3/api-docs") ||
                request.getRequestURI().contains("/token")
        )
            return true;

        String authorization = request.getHeader("AUTHORIZATION");

        if(authorization == null)
            throw new Exception("Authorization is null");

        if(authorization.startsWith("Bearer ")) {
            String token = authorization.substring(7);
            System.out.println(token);
        }
//        if(id == null)
//            throw new Exception();
//        else {
//            System.out.println(id);
//        }

        return true;
    }
}
