package br.com.fiap.EnergyMonitor.controller;

import br.com.fiap.EnergyMonitor.dto.equipment.CreateEquipmentDto;
import br.com.fiap.EnergyMonitor.dto.equipment.EquipmentOutputDto;
import br.com.fiap.EnergyMonitor.mappers.EquipmentMapper;
import br.com.fiap.EnergyMonitor.model.Equipment;
import br.com.fiap.EnergyMonitor.model.Sector;
import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.repository.SectorRepository;
import br.com.fiap.EnergyMonitor.service.AuthorizationService;
import br.com.fiap.EnergyMonitor.service.EquipmentService;
import br.com.fiap.EnergyMonitor.service.SectorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private SectorRepository sectorRepository;

    @PostMapping("/equipment")
    public ResponseEntity<EquipmentOutputDto> create(@RequestBody @Valid CreateEquipmentDto dto) {
        UserDetails userDetails = authService.getAuthenticatedUser();

        if (userDetails instanceof User user) {
            Sector sector = sectorRepository.findById(dto.sectorId())
                    .orElseThrow(() -> new EntityNotFoundException("Setor não encontrado"));

            Equipment equipment = EquipmentMapper.toEntity(dto, user, sector);
            Equipment created = equipmentService.create(equipment);
            return ResponseEntity.status(HttpStatus.CREATED).body(new EquipmentOutputDto(created));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/equipment")
    public ResponseEntity<List<EquipmentOutputDto>> listAll() {
        String email = authService.getAuthenticatedUser().getUsername();
        List<Equipment> list = equipmentService.findAllByUserEmail(email);
        return ResponseEntity.ok(list.stream().map(EquipmentOutputDto::new).toList());
    }

    @PostMapping("/equipment/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        String email = authService.getAuthenticatedUser().getUsername();
        equipmentService.activate(id, email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/equipment/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        String email = authService.getAuthenticatedUser().getUsername();
        equipmentService.deactivate(id, email);
        return ResponseEntity.ok().build();
    }
}
