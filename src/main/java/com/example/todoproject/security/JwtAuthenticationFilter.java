package com.example.todoproject.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    private String parseBearerToken(HttpServletRequest request){

        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴
        String bearerToken=request.getHeader("Authentication");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            log.info("Filter is running...");

            // 토큰 검사하기. JWT이므로 인가 서버에 요청하지 않고도 검증 가능 (?? 뭔소리)
            if(token!=null && !token.equalsIgnoreCase("null")){

                String userId=tokenProvider.validateAndGetUserId(token); // 토큰을 파싱해서 토큰의 사용자 아이디를 가져옴
                log.info("Authenticated user ID: "+userId);

                // 인증완료. SecurityContextHolder에 등록해야 인증된 사용자라고 생각한다.
                AbstractAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                        userId, // AuthenticationPrincipal
                        null,
                        AuthorityUtils.NO_AUTHORITIES
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            }
        }
        catch(Exception e){
            logger.error("Could not set user authentication in security context", e);
        }

        filterChain.doFilter(request, response);
    }

}
