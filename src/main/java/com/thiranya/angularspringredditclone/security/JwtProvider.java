package com.thiranya.angularspringredditclone.security;

import com.thiranya.angularspringredditclone.exception.RedditException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {
    private KeyStore keyStore;
    @Value("${reddit.keystore.password}")
    private String keyStorePassword;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/redditKeystore.jks");
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new RedditException("Exception occurred while loading keystore");
        }

    }

    public String generateToken(Authentication authentication) {
        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("redditKey", keyStorePassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RedditException("Exception occurred while retrieving private key from keystore");
        }
    }

    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJWT(String jwt) {
        Claims claims = parser()
                .setSigningKey(getPublickey())
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate("redditKey").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RedditException("Exception occurred while retrieving public key from keystore");
        }
    }
}