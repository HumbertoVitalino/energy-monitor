package br.com.fiap.EnergyMonitor.controller;

import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService service;

    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public User update(User user) {
        return service.update(user);
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
