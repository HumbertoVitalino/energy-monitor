package br.com.fiap.EnergyMonitor.controller;

import br.com.fiap.EnergyMonitor.mappers.SectorMapper;
import br.com.fiap.EnergyMonitor.dto.CreateSectorDto;
import br.com.fiap.EnergyMonitor.dto.SectorDto;
import br.com.fiap.EnergyMonitor.model.Sector;
import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.service.AuthorizationService;
import br.com.fiap.EnergyMonitor.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @Autowired
    private AuthorizationService authService;

    @PostMapping("/sector")
    public ResponseEntity<Sector> create(@RequestBody CreateSectorDto dto) {
        UserDetails userDetails = authService.getAuthenticatedUser();

        if (userDetails instanceof User) {
            User user = (User) userDetails;  // Faz o cast para User
            Sector sector = SectorMapper.MapToEntity(dto, user);
            Sector created = sectorService.create(sector);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("sector")
    public ResponseEntity<List<SectorDto>> getAll() {
        UserDetails user = authService.getAuthenticatedUser();
        List<Sector> sectors = sectorService.findAllByUserEmail(user.getUsername());
        List<SectorDto> dtos = SectorMapper.toDTOList(sectors);
        return ResponseEntity.ok(dtos);
    }
}
