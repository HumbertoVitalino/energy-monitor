package br.com.fiap.EnergyMonitor.service;

import br.com.fiap.EnergyMonitor.model.Equipment;
import br.com.fiap.EnergyMonitor.model.EquipmentUsageLog;
import br.com.fiap.EnergyMonitor.repository.EquipmentRepository;
import br.com.fiap.EnergyMonitor.repository.EquipmentUsageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EquipmentUsageLogRepository usageLogRepository;

    public Equipment create(Equipment equipment) {
        equipment.setActive(false);
        equipment.setLastActivatedAt(null);
        return equipmentRepository.save(equipment);
    }

    public List<Equipment> findAllByUserEmail(String email) {
        return equipmentRepository.findAllByUserEmail(email);
    }

    public void activate(Long id, String userEmail) {
        Equipment eq = getUserEquipment(id, userEmail);
        eq.setActive(true);
        eq.setLastActivatedAt(LocalDateTime.now());
        equipmentRepository.save(eq);
    }

    public void deactivate(Long id, String userEmail) {
        Equipment eq = getUserEquipment(id, userEmail);

        if (eq.isActive()) {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(eq.getLastActivatedAt(), now);
            double hours = duration.toMinutes() / 60.0;
            double consumption = eq.getConsumptionPerHour() * hours;

            EquipmentUsageLog log = new EquipmentUsageLog();
            log.setEquipment(eq);
            log.setStartedAt(eq.getLastActivatedAt());
            log.setEndedAt(now);
            log.setEstimatedConsumption(consumption);

            usageLogRepository.save(log);
        }

        eq.setActive(false);
        eq.setLastActivatedAt(null);
        equipmentRepository.save(eq);
    }

    public void autoDeactivateExceededEquipments() {
        List<Equipment> activeEquipments = equipmentRepository.findAllByActiveTrue();
        LocalDateTime now = LocalDateTime.now();

        for (Equipment eq : activeEquipments) {
            if (eq.getLastActivatedAt() != null) {
                Duration duration = Duration.between(eq.getLastActivatedAt(), now);
                if (duration.toHours() >= eq.getMaxActiveHours()) {
                    deactivate(eq.getId(), eq.getUser().getEmail());
                }
            }
        }
    }

    private Equipment getUserEquipment(Long id, String userEmail) {
        return equipmentRepository.findById(id)
                .filter(e -> e.getUser().getEmail().equals(userEmail))
                .orElseThrow(() -> new RuntimeException("Equipment cant be find or dont belong to user!"));
    }
}
