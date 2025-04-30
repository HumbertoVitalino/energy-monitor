package br.com.fiap.EnergyMonitor.service;

import br.com.fiap.EnergyMonitor.dto.CreateUserDto;
import br.com.fiap.EnergyMonitor.dto.UserOutputDto;
import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserOutputDto create(CreateUserDto user) {
        if (repository.findByEmail(user.email()) != null) {
            throw new RuntimeException("User already exists");
        }
        User entity = new User();
        entity.setEmail(user.email());
        entity.setPassword(new BCryptPasswordEncoder().encode(user.password()));
        entity.setName(user.name());

        return new UserOutputDto(repository.save(entity));
    }

    public UserOutputDto findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unable to find user with id: " + id));
        return new UserOutputDto(user);
    }

    public void deleteById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        repository.delete(user);
    }

    public User update(User user) {
        repository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        return repository.save(user);
    }
}
