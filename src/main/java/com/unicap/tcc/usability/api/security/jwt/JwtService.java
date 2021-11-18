package com.unicap.tcc.usability.api.security.jwt;

import com.unicap.tcc.usability.api.models.User;
import com.unicap.tcc.usability.api.models.dto.TokenDTO;
import com.unicap.tcc.usability.api.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class JwtService {

    private final SecurityProperties securityProperties;

    public TokenDTO generateToken(User user) {
        Long expirationString = Long.valueOf(securityProperties.getJwtExpiration());
        LocalDateTime dataHoraExpiration = LocalDateTime.now().plusMinutes(expirationString);
        Date date = Date.from(dataHoraExpiration.atZone(ZoneId.systemDefault()).toInstant());

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("roles", user.isAdmin() ? new String[]{"USER", "ADMIN"} : new String[]{"USER"});
        //claims.put("expiration", date);

        return TokenDTO.builder()
                .userEmail(user.getEmail())
                .isReviewer(user.getIsReviewer())
                .isAdmin(user.isAdmin())
                .name(user.getName())
                .username(user.getLogin())
                .userUid(user.getUid().toString())
                .accessToken(Jwts.builder()
                        .setClaims(claims)
                        .setSubject(user.getLogin())
                        .setExpiration(date)
                        .signWith(SignatureAlgorithm.HS512, securityProperties.getJwtSignKey())
                        .compact())
                .expirationTime(date.getTime())
                .build();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(securityProperties.getJwtSignKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean tokenValido(String token) {
        try {
            Claims claims = obterClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime date = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        } catch (Exception e) {
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return (String) obterClaims(token).getSubject();
    }
}
