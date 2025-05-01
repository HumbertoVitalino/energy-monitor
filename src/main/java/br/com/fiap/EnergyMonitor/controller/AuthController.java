package br.com.fiap.EnergyMonitor.controller;

import br.com.fiap.EnergyMonitor.dto.user.CreateUserDto;
import br.com.fiap.EnergyMonitor.dto.user.LoginDto;
import br.com.fiap.EnergyMonitor.dto.user.UserOutputDto;
import br.com.fiap.EnergyMonitor.model.User;
import br.com.fiap.EnergyMonitor.service.TokenService;
import br.com.fiap.EnergyMonitor.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDto userDto) {
        UsernamePasswordAuthenticationToken userPassword =
                new UsernamePasswordAuthenticationToken(
                        userDto.email(),
                        userDto.password()
                );
        Authentication auth = authenticationManager.authenticate(userPassword);

        String token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDto create(@RequestBody @Valid CreateUserDto userDto) {
        UserOutputDto userRegister = null;
        userRegister = service.create(userDto);
        return userRegister;
    }
}
