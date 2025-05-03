package com.nion.tasktracker.entity.dictionary;

import org.springframework.security.core.GrantedAuthority;

public enum TaskUserRole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
