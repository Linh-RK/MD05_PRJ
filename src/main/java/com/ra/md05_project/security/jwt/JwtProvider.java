package com.ra.md05_project.security.jwt;

import com.ra.md05_project.model.entity.ver1.Token;
import com.ra.md05_project.repository.TokenRepository;
import com.ra.md05_project.security.UserPrinciple;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class  JwtProvider {
    @Autowired
    private TokenRepository tokenRepository;
    @Value("${expired}")
    private Long EXPIRED;
    @Value("${secret_key}")
    private String SECRET_KEY;
//    private Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
    public String generateToken(UserPrinciple userPrinciple){
        // Tạo ra thời gian sống của token
        Date dateExpiration = new Date(new Date().getTime() + EXPIRED);
        // Tạo một UUID làm jwtID
        String jwtID = UUID.randomUUID().toString();
        return Jwts.builder()
                .setSubject(userPrinciple.getUsername())
                .setId(jwtID)
                .setExpiration(dateExpiration).
                signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
    }
    public Boolean validateToken(String token) {
        try {
            // Kiểm tra token hợp lệ về mặt cấu trúc
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);

            // Kiểm tra trong database để xác nhận token vẫn còn hợp lệ
            Optional<Token> tokenEntity = tokenRepository.findByToken(token);
            if (tokenEntity.isPresent() && tokenEntity.get().isValid()) {
                return true;
            }
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException exception) {
//            logger.error("Token validation failed: {}", exception.getMessage());
            log.error("Token validation failed: {}", exception.getMessage());
        }
        return false;
    }


    public String getUserNameFromToken(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

}