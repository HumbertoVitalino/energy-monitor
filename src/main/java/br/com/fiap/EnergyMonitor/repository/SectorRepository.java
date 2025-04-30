package br.com.fiap.EnergyMonitor.repository;

import br.com.fiap.EnergyMonitor.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    List<Sector> findAllByUserEmail(String email);
    Optional<Sector> findById(Long id);
}
