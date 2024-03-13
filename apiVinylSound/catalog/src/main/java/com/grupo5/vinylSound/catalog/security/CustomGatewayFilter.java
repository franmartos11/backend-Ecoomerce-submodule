package com.grupo5.vinylSound.catalog.security;

import com.grupo5.vinylSound.catalog.exception.UnauthorizedException;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomGatewayFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String proxyForwardedHostHeader = request.getHeader("X-Forwarded-Host");
    if (proxyForwardedHostHeader == null || !proxyForwardedHostHeader.equals("localhost:8089")) {
      UnauthorizedException unauthorizedException = new UnauthorizedException("Unauthorized Access, you should pass through the API gateway");
      byte[] responseToSend = unauthorizedException.getMessage().getBytes();
      ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
      ((HttpServletResponse) response).setStatus(401);
      response.getOutputStream().write(responseToSend);
      return;
    }
    filterChain.doFilter(request, response);
  }

}