package br.com.fiap.EnergyMonitor.controller;

import br.com.fiap.EnergyMonitor.mappers.SectorMapper;
import br.com.fiap.EnergyMonitor.dto.sector.CreateSectorDto;
import br.com.fiap.EnergyMonitor.dto.sector.SectorOutputDto;
import br.com.fiap.EnergyMonitor.model.Sector;
import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.service.AuthorizationService;
import br.com.fiap.EnergyMonitor.service.SectorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @Autowired
    private AuthorizationService authService;

    @PostMapping("/sector")
    public ResponseEntity<SectorOutputDto> create(@RequestBody @Valid CreateSectorDto dto) {
        UserDetails userDetails = authService.getAuthenticatedUser();

        if (userDetails instanceof User user) {
            Sector sector = SectorMapper.MapToEntity(dto, user);
            Sector created = sectorService.create(sector);
            return ResponseEntity.status(HttpStatus.CREATED).body(SectorMapper.toDTO(created));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/sector")
    public ResponseEntity<List<SectorOutputDto>> getAll() {
        UserDetails user = authService.getAuthenticatedUser();
        List<Sector> sectors = sectorService.findAllByUserEmail(user.getUsername());
        List<SectorOutputDto> dtos = SectorMapper.toDTOList(sectors);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/sector/{id}")
    public ResponseEntity<SectorOutputDto> getById(@PathVariable Long id) {
        UserDetails userDetails = authService.getAuthenticatedUser();
        Optional<Sector> sector = sectorService.findById(id, userDetails.getUsername());

        return sector
                .map(value -> ResponseEntity.ok(new SectorOutputDto(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping("/sector/{id}")
    public ResponseEntity<SectorOutputDto> update(@PathVariable Long id, @RequestBody @Valid CreateSectorDto dto) {
        UserDetails userDetails = authService.getAuthenticatedUser();
        Sector updated = sectorService.update(id, dto, userDetails.getUsername());
        return ResponseEntity.ok(SectorMapper.toDTO(updated));
    }

    @DeleteMapping("/sector/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        UserDetails userDetails = authService.getAuthenticatedUser();
        sectorService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
