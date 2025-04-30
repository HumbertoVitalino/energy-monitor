package br.com.fiap.EnergyMonitor.service;

import br.com.fiap.EnergyMonitor.model.Sector;
import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.repository.SectorRepository;
import br.com.fiap.EnergyMonitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
