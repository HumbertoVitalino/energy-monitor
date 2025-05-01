package br.com.fiap.EnergyMonitor.dto.equipment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateEquipmentDto(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Consumption per hour is required")
        Double consumptionPerHour,

        @NotNull(message = "Max active hours is required")
        Integer maxActiveHours,

        @NotNull(message = "Sector ID is required")
        Long sectorId
) {}
