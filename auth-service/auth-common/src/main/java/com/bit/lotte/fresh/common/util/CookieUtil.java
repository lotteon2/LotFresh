package com.bit.lotte.fresh.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void setCookie(HttpServletResponse response, String name, String value,
        int maxAge) {
      Cookie cookie = new Cookie(name, value);
      cookie.setMaxAge(maxAge);
      response.addCookie(cookie);
    }

  public static String getCookie(HttpServletRequest request, String name) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(name)) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  public static void clearCookie(HttpServletResponse response, String name) {
    Cookie cookie = new Cookie(name, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
