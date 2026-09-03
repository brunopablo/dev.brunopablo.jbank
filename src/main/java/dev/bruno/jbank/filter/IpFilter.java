package dev.bruno.jbank.filter;


import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class IpFilter extends HttpFilter{

    @Override
    protected void doFilter(HttpServletRequest request, 
                            HttpServletResponse response, 
                            FilterChain chain) throws IOException, ServletException
    {
    
        var ipAddress = request.getRemoteAddr();

        request.setAttribute("x-user-ip", ipAddress);

        response.setHeader("x-user-ip", ipAddress);

        chain.doFilter(request, response);

    }


    

    
}