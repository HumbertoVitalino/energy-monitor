package br.com.fiap.EnergyMonitor.dto;

public record SectorDto(
        Long id,
        String name,
        double consumptionLimit
) {
}
