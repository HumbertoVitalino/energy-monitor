package br.com.fiap.EnergyMonitor.dto;


import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.model.UserRoles;

public record UserOutputDto(
        Long id,
        String name,
        String email,
        UserRoles role
) {
    public UserOutputDto(User user) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
