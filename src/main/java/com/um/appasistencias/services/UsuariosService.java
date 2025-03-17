package com.um.appasistencias.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.um.appasistencias.models.Usuarios;
import com.um.appasistencias.repositories.UsuariosRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuariosService implements ReactiveUserDetailsService {
    @Autowired private UsuariosRepository usuariosRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public Flux<Usuarios> findAll() {
        return usuariosRepository.findAll();
    }

    public Mono<Usuarios> findById(String id) {
        UUID uuid = UUID.fromString(id);
        return usuariosRepository.findByUuid(uuid);
    }

    public Mono<Void> deleteById(String id) {
        UUID uuid = UUID.fromString(id);
        return usuariosRepository.deleteByUuid(uuid);
    }

    public Mono<Boolean> exists(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return usuariosRepository.exists(uuid);
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    public Mono<Usuarios> update(String id, Usuarios user) {
        UUID uuid = UUID.fromString(id);
        return usuariosRepository.update(
            user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getRole(), user.getNombres(), user.getApellidos(),
            user.getNumempleado(), user.getStatus(), user.isActivo(), user.getEmail(), uuid
        );
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return usuariosRepository.findByUsername(username)
            .switchIfEmpty(Mono.error(new UsernameNotFoundException("Username not found: " + username)))
            .map(user -> new Usuarios(
                user.getUsername(), 
                user.getPassword(), 
                user.getRole(), 
                user.getNombres(), 
                user.getApellidos(), 
                user.getNumempleado(), 
                user.getStatus(), 
                user.isActivo(), 
                user.getEmail()
            ));
    }

    public Mono<Usuarios> register(String username, String password, String role, String nombres, String apellidos, String numempleado, String status, String email) {
        return usuariosRepository.save(new Usuarios(username, passwordEncoder.encode(password), role, nombres, apellidos, numempleado, status, true, email));
    }
}
