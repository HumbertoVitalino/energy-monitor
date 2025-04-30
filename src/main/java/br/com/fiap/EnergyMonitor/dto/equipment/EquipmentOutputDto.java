package br.com.fiap.EnergyMonitor.dto.equipment;

import br.com.fiap.EnergyMonitor.dto.sector.SectorOutputDto;
import br.com.fiap.EnergyMonitor.model.Equipment;

public record EquipmentOutputDto(
        Long id,
        String name,
        boolean active,
        double consumptionPerHour,
        int maxActiveHours,
        SectorOutputDto sector
) {
    public EquipmentOutputDto(Equipment eq) {
        this(
                eq.getId(),
                eq.getName(),
                eq.isActive(),
                eq.getConsumptionPerHour(),
                eq.getMaxActiveHours(),
                new SectorOutputDto(eq.getSector())
        );
    }
}
