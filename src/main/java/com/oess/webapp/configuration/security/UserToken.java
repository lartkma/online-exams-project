package com.oess.webapp.configuration.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class UserToken implements Authentication {

    private static final long serialVersionUID = -4951070845629599204L;

    private String email;
    private String humanName;
    private long userId;
    private boolean isAuthenticated;
    private List<UserRole> authorities;

    public UserToken(long userId, String humanName, String email, UserRole role) {
        this.userId = userId;
        this.humanName = humanName;
        this.email = email;
        this.authorities = Arrays.asList(role);
        this.isAuthenticated = true;
    }

    @Override
    public String getName() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.email;
    }

    public String getHumanName() {
        return humanName;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Token can't be authenticated manually");
        } else {
            this.isAuthenticated = false;
        }
    }

    @Override
    public String toString() {
        return this.email + " [USER-ID:" + this.userId + "]";
    }

}
