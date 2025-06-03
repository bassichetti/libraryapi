package com.library.libraryapi.service;

import com.library.libraryapi.config.JwtService;
import com.library.libraryapi.domain.entity.Usuario;
import com.library.libraryapi.domain.repository.UsuarioRepository;
import com.library.libraryapi.dto.AuthenticationRequest;
import com.library.libraryapi.dto.AuthenticationResponse;
import com.library.libraryapi.dto.RegisterRequest;
import com.library.libraryapi.dto.UserDetailsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

        private final UsuarioRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthenticationResponse register(RegisterRequest request) {
                var user = Usuario.builder()
                                .nome(request.getNome())
                                .email(request.getEmail())
                                .senha(passwordEncoder.encode(request.getSenha()))
                                .role("USER")
                                .build();
                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        public AuthenticationResponse authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getEmail(),
                                                request.getSenha()));
                var user = repository.findByEmail(request.getEmail())
                                .orElseThrow();
                var jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build();
        }

        public UserDetailsResponse getUserDetails(UserDetails userDetails) {
                Usuario user = repository.findByEmail(userDetails.getUsername())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

                return UserDetailsResponse.builder()
                                .id(user.getId())
                                .nome(user.getNome())
                                .email(user.getEmail())
                                .role(user.getRole())
                                .build();
        }
}