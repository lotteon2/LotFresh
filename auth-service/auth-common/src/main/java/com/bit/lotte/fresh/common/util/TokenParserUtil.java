package com.bit.lotte.fresh.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class TokenParserUtil {

  public static String extractNumericPart(String input) {
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(input);

    if (matcher.find()) {
      return matcher.group();
    } else {
      return null;
    }
  }

  public String extractToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7);
    }
    return null;
  }

}
