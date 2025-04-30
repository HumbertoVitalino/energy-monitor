package br.com.fiap.EnergyMonitor.repository;

import br.com.fiap.EnergyMonitor.model.EquipmentUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentUsageLogRepository extends JpaRepository<EquipmentUsageLog, Long> {
}
