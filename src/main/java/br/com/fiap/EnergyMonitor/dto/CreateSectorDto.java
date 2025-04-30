package br.com.fiap.EnergyMonitor.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateSectorDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Limit is required")
        Double consumptionLimit
) {
}
