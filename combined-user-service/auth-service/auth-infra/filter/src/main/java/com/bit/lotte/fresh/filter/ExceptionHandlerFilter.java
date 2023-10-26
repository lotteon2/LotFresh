package com.bit.lotte.fresh.filter;

import com.bit.lotte.fresh.application.handler.ErrorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (RuntimeException e) {

      ErrorDTO dto = new ErrorDTO(String.valueOf(response.getStatus()),
          HttpStatus.valueOf(response.getStatus()),
          e.getMessage());
      log.info(e.getMessage());
      response.setCharacterEncoding("UTF-8");
      response.setStatus(HttpStatus.valueOf(401).value());
      response.getWriter().write(convertObjectToJson(dto));
    }
  }

  public String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(object);
  }
}