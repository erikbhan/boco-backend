package no.ntnu.idatt2106.middleware;

import no.ntnu.idatt2106.exception.StatusCodeException;
import no.ntnu.idatt2106.util.TokenUtil;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RequireAuthAspect {

        @Before(value = "@within(RequireAuth) || @annotation(RequireAuth)")
        public void requireAuth(JoinPoint joinpoint) throws Exception {
                String token = TokenUtil.getToken();
                if(token == null || token.isBlank() || token.isEmpty()){
                        throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }

                MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
                Method method = methodSignature.getMethod();

                RequireAuth requireAuth = joinpoint.getTarget().getClass().getAnnotation(RequireAuth.class);
                if (requireAuth == null) {
                        requireAuth = method.getAnnotation(RequireAuth.class);
                }

                if(requireAuth == null) {
                        throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }

                if(!TokenUtil.verifyToken(token)) {
                        throw new StatusCodeException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }

        }
}