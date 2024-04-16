package _com6.Parcial.Corte2.util;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.logging.Logger;

@Aspect
@Component
public class EndpointsLogger {
    private static final Logger LOGGER = Logger.getLogger(EndpointsLogger.class.getName());

    @Pointcut("within(_com6.Parcial.Corte2.controller.*)")
    public void controllerPointcut() {
    }

    @Before("controllerPointcut()")
    public void logMethodAccess(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String clientIP = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        String httpMethod = request.getMethod();

        LOGGER.info(STR."Accessed endpoint: \{className}.\{methodName}, URL: \{url}, HTTP Method: \{httpMethod}, Client IP: \{clientIP}");
    }
}
