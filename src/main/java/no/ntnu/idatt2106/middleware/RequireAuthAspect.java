package no.ntnu.idatt2106.middleware;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public class RequireAuthAspect {

        @Before(value = "@within(RequireAuth) || @annotation(RequireAuth)")
        public void requireAuth(JoinPoint joinpoint) throws Exception {
                String token = TokenUtil.getToken();

                if(token == null || token.isBlank() || token.isEmpty()) throw new Exception("No token found");

                MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
                Method method = methodSignature.getMethod();

                RequireAuth requireAuth = joinpoint.getTarget().getClass().getAnnotation(RequireAuth.class);
                if (requireAuth == null) {
                        requireAuth = method.getAnnotation(RequireAuth.class);
                }

                if(requireAuth == null) {
                        // Throw exception
                }

                if(!TokenUtil.verifyToken(token)) {
                        throw new Exception("Invalid token");
                }
        }
}