package messenger.backend.auth.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import messenger.backend.auth.exceptions.JwtAuthException;
import messenger.backend.user.UserEntity;
import messenger.backend.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.validity}")
    private long validityInMilliseconds;
    @Value("${jwt.header}")
    private String authHeader;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        Claims claims = Jwts.claims();
        claims.put("id", userDto.getId());
        claims.put("username", userDto.getUsername());
        claims.put("fullName", userDto.getFullName());

        Date now = new Date();
        Date expiration = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public UserDto getUserDto(String token) {
         Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
         return UserDto.builder()
                 .id(claims.get("id", String.class))
                 .username(claims.get("username", String.class))
                 .fullName(claims.get("fullName", String.class))
                 .build();
    }

    public UserDto getUserDto(HttpServletRequest request) {
        return getUserDto(resolveToken(request));
    }

    public String getUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id", String.class);
    }

    public String getUserId(HttpServletRequest request) {
        return getUserId(resolveToken(request));
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getUsername(HttpServletRequest request) {
        return getUsername(resolveToken(request));
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authHeader);
    }

}
