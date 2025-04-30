package br.com.fiap.EnergyMonitor.dto.sector;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSectorDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Limit is required")
        @DecimalMin(value = "0.01", message = "Consumption limit must be greater than zero")
        Double consumptionLimit
) {
}
