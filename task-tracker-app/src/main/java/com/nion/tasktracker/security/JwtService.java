package com.nion.tasktracker.security;

import com.nion.tasktracker.entity.TaskUserEntity;
import com.nion.tasktracker.handler.exception.TokenNotFoundException;
import com.nion.tasktracker.repository.ITokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    @Value( "${security.jwt.secret_key}")
    private String secretKey;

    @Value("${security.jwt.access_token_expiration}")
    private Long accessTokenExp;

    @Value("${security.jwt.refresh_token_expiration}")
    private Long refreshTokenExp;

    private final ITokenRepository tokenRepository;

    public JwtService(ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generateAccessToken(TaskUserEntity user) {
        return generateToken(user, accessTokenExp);
    }

    public String generateRefreshToken(TaskUserEntity user){
        return generateToken(user, refreshTokenExp);
    }
    public boolean isValid(String token){
        var userId = Long.parseLong(extractUserId(token));
        log.info("user id: {}", userId);

        var foundToken = tokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new TokenNotFoundException("not found token"));
        var tokenUser =  foundToken.getTaskUserEntity().getUserId();
        log.info("token user id: {}", tokenUser);

        return tokenUser.equals(userId) && !isTokenExpired(token);

    }
    public boolean isValidRefresh(String token){
        var userId = Long.parseLong(extractUserId(token));

        var foundTokens = tokenRepository.findByRefreshToken(token);
        boolean isValidRefreshToken = foundTokens
                .map(tokenInfo -> !tokenInfo.isRevoked() && !tokenInfo.isExpired()).orElse(false);

        return userId == foundTokens.get().getTaskUserEntity().getUserId() && isValidRefreshToken && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        return extractExpiration(token)
                .before(new Date());
    }

    public Date extractExpiration(String token){
        return extractClaims(
                token,
                Claims::getExpiration);
    }

    public String extractUserId(String token){
        return extractClaims(
                token,
                Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> resolver){
        var claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /** private method to return claims from token*/
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /** private method to generate jwt token */
    private String generateToken(TaskUserEntity user, long time) {
        return Jwts.builder()
                .subject(user.getUserId().toString())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + time))
                .signWith(getSecretKey())
                .compact();
    }

    /** private method to return secret key before decode */
    private SecretKey getSecretKey() {
        return Keys
                .hmacShaKeyFor(Decoders
                        .BASE64
                        .decode(secretKey
                                .toString()));
    }
}
