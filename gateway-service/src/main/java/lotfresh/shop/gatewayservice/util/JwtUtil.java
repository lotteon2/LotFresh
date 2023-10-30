
package lotfresh.shop.gatewayservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;

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

    private Long getUserId(Claims claims) {
        return claims.get("userId", Long.class);
    }

    public void addJwtPayloadHeaders(ServerHttpRequest request, Claims claims) {
        request.mutate()
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("userId", String.valueOf(getUserId(claims)))
                .build();
    }

    public void addJwtPayloadHeadersForProductService(ServerHttpRequest request, Claims claims) {
        Long userId = (claims != null) ? getUserId(claims) : null;
        request.mutate()
                .header("Content-Type", "application/json;charset=UTF-8")
                .header("userId", String.valueOf(1L))
                // .header("userId", userId == null ? null: String.valueOf(userId))
                .build();
    }
}