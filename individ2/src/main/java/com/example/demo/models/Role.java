package com.example.demo.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, PRODA, SKLAD, KADRI;

    @Override
    public String getAuthority() {
        return name();
    }
}
