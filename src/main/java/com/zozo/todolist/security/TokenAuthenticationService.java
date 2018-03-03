package com.zozo.todolist.security;

import com.zozo.todolist.models.User;
import com.zozo.todolist.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security
        .authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Collections.emptyList;

public class TokenAuthenticationService {
    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static public void addAuthenticationHeader(HttpServletResponse res, User user) {

        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put("role", user.getRole());

        String JWT = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    static public void authenticateOnLogin( AuthenticationManager authenticationManager, User user,
                                            HttpServletResponse response) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        Arrays.asList(new SimpleGrantedAuthority(user.getRole()))
                )
        );
        System.out.println("authentication" + authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenAuthenticationService.addAuthenticationHeader(response, user);
    }

    static Authentication extractAuthenticationFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null && !RedisUtil.INSTANCE.sismember(RedisUtil.BLACK_LISTS_TOKENS, token)) {
            // parse the token.
            Claims  claim = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            String userName = claim.getSubject();
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority((String) claim.get("role"));

            return userName != null ?
                    new UsernamePasswordAuthenticationToken(userName, null, Arrays.asList(authority)) :
                    null;
        }
        return null;
    }

    static public void invalidateToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            RedisUtil.INSTANCE.sadd(RedisUtil.BLACK_LISTS_TOKENS, token);
        }
    }
}
