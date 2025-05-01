package br.com.fiap.EnergyMonitor.repository;

import br.com.fiap.EnergyMonitor.model.EquipmentUsageLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentUsageLogRepository extends JpaRepository<EquipmentUsageLog, Long> {
    Page<EquipmentUsageLog> findByEquipmentUserEmail(String email, Pageable pageable);
    Page<EquipmentUsageLog> findByEquipmentIdAndEquipmentUserEmail(Long equipmentId, String email, Pageable pageable);
}
