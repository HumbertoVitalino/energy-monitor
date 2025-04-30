package br.com.fiap.EnergyMonitor.dto;

import br.com.fiap.EnergyMonitor.model.Sector;

public record SectorOutputDto(
        Long id,
        String name,
        double consumptionLimit,
        UserOutputDto user
) {
    public SectorOutputDto(Sector sector) {
        this(
                sector.getId(),
                sector.getName(),
                sector.getConsumptionLimit(),
                new UserOutputDto(sector.getUser())
        );
    }
}
