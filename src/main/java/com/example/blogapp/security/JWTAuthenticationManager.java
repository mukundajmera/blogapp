package com.example.blogapp.security;

import com.example.blogapp.users.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

public class JWTAuthenticationManager implements AuthenticationManager {

    private JWTService jwtService;
    private UsersService usersService;

    public JWTAuthenticationManager(JWTService jwtService, UsersService usersService) {
        this.jwtService = jwtService;
        this.usersService = usersService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if(authentication instanceof JWTAuthentication) {

            var jwtAuthentication = (JWTAuthentication) authentication;
            var jwt = jwtAuthentication.getCredentials();
            var userId = jwtService.retrieveUserId(jwt);
            var userEntitiy = usersService.getUser(userId);
            jwtAuthentication.userId = userEntitiy;
            jwtAuthentication.setAuthenticated(true);

            SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);
            return jwtAuthentication;

        } else{
            throw new IllegalAccessError("Cannot authenticate without JWT token");
        }
    }
}
