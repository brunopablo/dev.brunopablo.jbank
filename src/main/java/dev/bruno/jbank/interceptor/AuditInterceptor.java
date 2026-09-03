package dev.bruno.jbank.interceptor;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditInterceptor implements HandlerInterceptor{

    private final Logger logger = LoggerFactory.getLogger(AuditInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest request, 
                                HttpServletResponse response, 
                                Object handler,
                                @Nullable Exception ex) throws Exception
    {
    
        logger.info("Audit - Method: {}, URL: {}, IpAddress: {}, Status: {}",
            request.getMethod(),
            request.getRequestURI(),
            request.getAttribute("x-user-ip"),
            response.getStatus()
        );

    }
}