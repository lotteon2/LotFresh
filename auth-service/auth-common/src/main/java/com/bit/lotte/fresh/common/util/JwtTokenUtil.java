package com.bit.lotte.fresh.common.util;


import com.bit.lotte.fresh.common.instant.LoginSessionTime;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class JwtTokenUtil {

  public static final String roleKeyName = "role";
  public static final String categoryAdminSubCategoryIdList = "category_admin_description";
  private static final SecretKey SECRET_KEY = createSecretKey();

  public static SecretKey createSecretKey() {
    byte[] decodedKey = Base64.getDecoder().decode("LOTTE_FRESH");
    return new SecretKeySpec(decodedKey, 0, decodedKey.length, "HmacSHA256");
  }

  public static String generateToken(String subject, String customClaimKey, String customClaimValue,
      Date expiration) {
    Date now = new Date();
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .claim(customClaimKey, customClaimValue)
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public static boolean verifyToken(String token) {

    try {
      Claims claims = Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();

      Date expiration = claims.getExpiration();
      if (!expiration.before(new Date())) {
        throw new JwtException("로그인 시간이 만료되었습니다.");
      }
      return true;
    } catch (UnsupportedJwtException e) {
      throw new JwtException("토큰 값이 일치하지 않습니다.");
    }
  }

  public static boolean checkCategoryAdminHasSubIdList(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
    return claims.containsKey(categoryAdminSubCategoryIdList);
  }

  public static String parseCategoryAdminCategoryId(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)
        .getBody();
    String role = (String) claims.get(roleKeyName);
    return TokenParserUtil.extractNumericPart(role);
  }

  public static String setCategoryAdminSubIdList(String token, String description) {

      if (checkCategoryAdminHasSubIdList(token)) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();

        claims.put(categoryAdminSubCategoryIdList, description);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(LoginSessionTime.LOGIN_SESSION_SEC))
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
      } else {
        throw new JwtException("존재하지 않는 토큰 정보입니다.");
      }
  }

  public static String getRoleString(String token) {
    try {

      Claims claims = Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();
      return claims.get(roleKeyName, String.class);

    } catch (NullPointerException e) {
      throw new NullPointerException("존재하지 않는 claims key입니다.");
    }
  }

  public static String getSubjectFromToken(String token) {
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();

      return claims.getSubject();
    } catch (UnsupportedJwtException exception) {
      throw new UnsupportedJwtException("해당 토큰 값으로 claims을 얻을 수 없습니다.");
    }
  }

  /**
   * @param token
   * @return 해당 null값은 verified하지 않으면 에러가 던져지므로 null을 참조할 일이 없습니다.
   */
  public static String getNewTokenFromOldToken(String token) {
    if (verifyToken(token)) {
      Claims claims = Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .parseClaimsJws(token)
          .getBody();
      String subject = claims.getSubject();
      String claimsKey = roleKeyName; // Replace with your custom claim key
      String claimsValue = claims.get(claimsKey)
          .toString(); // Replace with your custom claim value

      // Generate a new token using the extracted claims
      return generateToken(subject, claimsKey, claimsValue,
          new Date(LoginSessionTime.LOGIN_SESSION_SEC));
    }
    return null;
  }

}




