package com.example.blogapp.security;

import com.example.blogapp.users.UserEntitiy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JWTAuthentication implements Authentication {

    String jwtToken;
    UserEntitiy userId;

    public JWTAuthentication(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    /**
     * Return the principle of the {@code Authentication} request.
     * The "principle" is the entitiy that is being authenticated.
     * In this case it is the User.
     * @return
     */
    @Override
    public UserEntitiy getPrincipal() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return userId != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "";
    }
}
