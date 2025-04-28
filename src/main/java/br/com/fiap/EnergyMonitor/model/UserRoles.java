package br.com.fiap.EnergyMonitor.model;

import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN("admin"),
    USER("user");

    private String role;

    UserRoles(String role) {
        this.role = role;
    }
}
