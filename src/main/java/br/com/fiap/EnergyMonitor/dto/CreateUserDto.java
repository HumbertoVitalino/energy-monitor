package br.com.fiap.EnergyMonitor.dto;

import br.com.fiap.EnergyMonitor.model.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank(message = "Name field is required")
        String name,

        @NotBlank(message = "Email field is required")
        @Email
        String email,

        @NotBlank(message = "Password field is required, min 6 and max 20")
        @Size(min = 6, max = 20)
        String password,

        UserRoles role
) {
}
