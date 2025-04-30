package br.com.fiap.EnergyMonitor.service;

import br.com.fiap.EnergyMonitor.dto.CreateSectorDto;
import br.com.fiap.EnergyMonitor.model.Sector;
import br.com.fiap.EnergyMonitor.repository.SectorRepository;
import br.com.fiap.EnergyMonitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {

    @Autowired
    private SectorRepository repository;

    @Autowired
    private UserRepository userRepository;

    public Sector create(Sector sector) {
        return repository.save(sector);
    }

    public List<Sector> findAllByUserEmail(String userEmail) {
        return repository.findAllByUserEmail(userEmail);
    }

    public Optional<Sector> findById(Long id, String userEmail) {
        Optional<Sector> sectorOptional = repository.findById(id);

        if (sectorOptional.isPresent()) {
            Sector sector = sectorOptional.get();
            if (!sector.getUser().getEmail().equals(userEmail)) {
                throw new RuntimeException("Sector dont belong to user");
            }
            return sectorOptional;
        } else {
            throw new RuntimeException("Sector not found!");
        }
    }

    public Sector update(Long id, CreateSectorDto dto, String userEmail) {
        Sector sector = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sector not found!"));

        if (!sector.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Sector dont belong to user");
        }

        sector.setName(dto.name());
        sector.setConsumptionLimit(dto.consumptionLimit());

        return repository.save(sector);
    }

    public void delete(Long id, String userEmail) {
        Sector sector = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sector not found"));

        if (!sector.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Sector dont belong to user");
        }

        repository.delete(sector);
    }
}
