package com.example.blogapp.security;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JWTServiceTests {

    JWTService jwtService = new JWTService();

    @Test
    void canCreateJWTFromUsername(){
        var jwt = jwtService.createJWT(1001L);
        Assertions.assertNotNull(jwt);
    }
}
