package br.com.fiap.EnergyMonitor.repository;

import br.com.fiap.EnergyMonitor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);
}