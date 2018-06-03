package com.oess.webapp.configuration.security;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    STUDENT("ROLE_STUDENT"),
    TEACHER("ROLE_TEACHER");
    
    private String roleId;

    private UserRole(String roleId) {
        this.roleId = roleId;
    }
    
    @Override
    public String getAuthority() {
        return this.roleId;
    }

}
