package br.com.fiap.EnergyMonitor.controller;

import br.com.fiap.EnergyMonitor.dto.equipment.EquipmentUsageLogDto;
import br.com.fiap.EnergyMonitor.model.EquipmentUsageLog;
import br.com.fiap.EnergyMonitor.service.AuthorizationService;
import br.com.fiap.EnergyMonitor.repository.EquipmentUsageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EquipmentUsageLogController {

    @Autowired
    private EquipmentUsageLogRepository logRepository;

    @Autowired
    private AuthorizationService authService;

    @GetMapping("/equipment-logs")
    public ResponseEntity<Page<EquipmentUsageLogDto>> getAllLogsByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String email = authService.getAuthenticatedUser().getUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by("startedAt").descending());
        Page<EquipmentUsageLog> logs = logRepository.findByEquipmentUserEmail(email, pageable);
        Page<EquipmentUsageLogDto> dtoPage = logs.map(EquipmentUsageLogDto::new);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/equipment/{equipmentId}/logs")
    public ResponseEntity<Page<EquipmentUsageLogDto>> getLogsByEquipment(
            @PathVariable Long equipmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String email = authService.getAuthenticatedUser().getUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by("startedAt").descending());
        Page<EquipmentUsageLog> logs = logRepository.findByEquipmentIdAndEquipmentUserEmail(equipmentId, email, pageable);
        Page<EquipmentUsageLogDto> dtoPage = logs.map(EquipmentUsageLogDto::new);
        return ResponseEntity.ok(dtoPage);
    }
}
