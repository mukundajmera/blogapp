package com.example.blogapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
    //TODO move to property files
    private static final String JWT_KEY = "jsadgbkasdbg457hjhsadbgkji123lewrjha";

    private final Algorithm algorithm = Algorithm.HMAC256(JWT_KEY);


    public String createJWT(Long username) {
        return JWT.create()
                .withSubject(username.toString())
                .withIssuedAt(new Date())
                .sign(algorithm);
//                .withExpiresAt(new Date()) //TODO setup date
    }

    public Long retrieveUserId(String jwtString) {
        var decoded = JWT.decode(jwtString);
        return Long.valueOf(decoded.getSubject());
    }

}
