package com.example.todoproject.security;

import com.example.todoproject.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

    private static final String SECRET_KEY="NMA8JPctFuna59f5";

    public String create(UserEntity userEntity){ // JWT 토큰 생성

        Date expiryDate=Date.from(Instant.now().plus(1, ChronoUnit.DAYS));

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY) // header에 들어갈 내용 및 서명을 하기 위한 SECRET KEY
                // payload에 들어갈 내용
                .setSubject(userEntity.getId()) // sub
                .setIssuer("demo app") // iss
                .setIssuedAt(new Date()) // iat
                .setExpiration(expiryDate) // exp
                .compact();

    }

    public String validateAndGetUserId(String token){ // 토큰을 디코딩, 파싱하고 토큰의 위조 여부 확인

        Claims claims= Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJwt(token)
                .getBody();

        return claims.getSubject(); // 사용자의 아이디 리턴
    }
}
