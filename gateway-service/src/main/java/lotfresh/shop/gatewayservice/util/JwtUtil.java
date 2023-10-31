
package lotfresh.shop.gatewayservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;

    private final JwtParser jwtParser;

    public JwtUtil(Environment env) {
        this.key = new SecretKeySpec(
                DatatypeConverter.parseBase64Binary(env.getProperty("jwt.secret")),
                SignatureAlgorithm.HS256.getJcaName());
        this.jwtParser = Jwts.parser();
    }

    public Claims parse(String jwt) {
        return jwtParser.setSigningKey(key).parseClaimsJws(jwt).getBody();
    }

    private String getUserId(Claims claims) {
        return claims.get("userId", String.class);
    }

    public void addJwtPayloadHeaders(ServerHttpRequest request, Claims claims) {
        ServerHttpRequest mutatedRequest = request.mutate()
                .header("Content-Type", "application/json;charset=UTF-8")
                //                .header("userId", String.valueOf(1L))
                .header("userId", getUserId(claims))
                .build();
        log.warn("그래서 이렇게 변환되었어요.- 변환전 헤더정보" + request.getHeaders().toString());
        log.warn("그래서 이렇게 변환되었어요.- 변환전 바디정보" + request.getBody().toString());
        log.warn("그래서 이렇게 변환되었어요.- 변환후 헤더정보" + mutatedRequest.getHeaders().toString());
        log.warn("그래서 이렇게 변환되었어요.- 변환후 바디정보" + mutatedRequest.getBody().toString());
    }

    public void addJwtPayloadHeadersForProductService(ServerHttpRequest request, Claims claims) {
        String userId = (claims != null) ? getUserId(claims) : null;
        request.mutate()
                .header("Content-Type", "application/json;charset=UTF-8")
//                .header("userId", String.valueOf(1L))
                 .header("userId", userId == null ? null: String.valueOf(userId))
                .build();
        log.warn("그래서 이렇게 변환되었어요." + request.toString());
    }
}