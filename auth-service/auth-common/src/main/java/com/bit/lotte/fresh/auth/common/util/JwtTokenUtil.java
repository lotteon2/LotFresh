package com.bit.lotte.fresh.auth.common.util;


import com.bit.lotte.fresh.auth.common.instant.LoginSessionTime;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class JwtTokenUtil {

  public static final String roleKeyName = "role";
  public static final String categoryAdminSubCategoryIdList = "category_admin_description";
//  @Value("${jwt.secret}")
  private static String JWT_SECRET="lotfresh1!lotfresh1!lotfresh1!lotfresh1!lotfresh1!";


  public static String generateToken(String subject, String customClaimKey, String customClaimValue,
      Date expiration) {
    Date now = new Date();
    log.info("secret:"+ JWT_SECRET);
    return Jwts.builder()
        .setSubject(subject)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .claim(customClaimKey, customClaimValue)
        .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
        .compact();
  }

  public static void verifyToken(String token) {

    try {
      Claims claims = Jwts.parser()
          .setSigningKey(JWT_SECRET)
          .parseClaimsJws(token)
          .getBody();

      Date expiration = claims.getExpiration();
      if (expiration.before(new Date(System.currentTimeMillis()))) {
        throw new JwtException("로그인 시간이 만료되었습니다.");
      }
    } catch (SignatureException e) {
      throw new SignatureException("잘못된 접근입니다. 암호화 키가 다릅니다.");
    } catch (MalformedJwtException e) {
      throw new MalformedJwtException("일치하지 않는 토큰입니다.");
    }


  }

  public static boolean checkCategoryAdminHasSubIdList(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(JWT_SECRET)
        .parseClaimsJws(token)
        .getBody();
    return claims.containsKey(categoryAdminSubCategoryIdList);
  }

  public static String parseCategoryAdminCategoryId(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(JWT_SECRET)
        .parseClaimsJws(token)
        .getBody();
    String role = (String) claims.get(roleKeyName);
    return TokenParserUtil.extractNumericPart(role);
  }

  public static String setCategoryAdminSubIdList(String token, String description) {

      if (checkCategoryAdminHasSubIdList(token)) {
        Claims claims = Jwts.parser()
            .setSigningKey(JWT_SECRET)
            .parseClaimsJws(token)
            .getBody();

        claims.put(categoryAdminSubCategoryIdList, description);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date())
            .setExpiration(new Date(LoginSessionTime.LOGIN_SESSION_SEC))
            .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
            .compact();
      } else {
        throw new JwtException("존재하지 않는 토큰 정보입니다.");
      }
  }

  public static String getRoleString(String token) {
    try {

      Claims claims = Jwts.parser()
          .setSigningKey(JWT_SECRET)
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
          .setSigningKey(JWT_SECRET)
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
    if (token != null) {
      verifyToken(token);
      Claims claims = Jwts.parser()
          .setSigningKey(JWT_SECRET)
          .parseClaimsJws(token)
          .getBody();
      String subject = claims.getSubject();
      String claimsKey = roleKeyName;
      String claimsValue = claims.get(claimsKey)
          .toString();
      return generateToken(subject, claimsKey, claimsValue,
          new Date(System.currentTimeMillis() + LoginSessionTime.LOGIN_SESSION_SEC * 1000L));
    }
    return null;
  }

}




