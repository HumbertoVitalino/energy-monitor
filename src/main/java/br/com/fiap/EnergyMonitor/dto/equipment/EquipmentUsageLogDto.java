package br.com.fiap.EnergyMonitor.dto.equipment;

import br.com.fiap.EnergyMonitor.model.EquipmentUsageLog;

import java.time.LocalDateTime;

public record EquipmentUsageLogDto(
        Long id,
        Long equipmentId,
        LocalDateTime startedAt,
        LocalDateTime endedAt,
        Double estimatedConsumption
) {
    public EquipmentUsageLogDto(EquipmentUsageLog log) {
        this(
                log.getId(),
                log.getEquipment().getId(),
                log.getStartedAt(),
                log.getEndedAt(),
                log.getEstimatedConsumption()
        );
    }
}