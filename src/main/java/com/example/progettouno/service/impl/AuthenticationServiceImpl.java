package com.example.progettouno.service.impl;

import com.example.progettouno.dto.AuthenticationRequestDTO;
import com.example.progettouno.dto.AuthenticationResponseDTO;
import com.example.progettouno.exception.UserNotFoundException;
import com.example.progettouno.repository.UtenteRepository;
import com.example.progettouno.service.AuthenticationService;
import com.example.progettouno.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UtenteRepository utenteRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(UtenteRepository utenteRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.utenteRepository = utenteRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {

            // 1. Autentica l'utente usando solo le credenziali (email e password)
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );


        // 2. Se l'autenticazione sopra ha successo, cerca l'utente (UserDetails)
        var utente = utenteRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato"));

        // 3. Genera il token JWT
       var jwtToken = jwtService.generateToken(utente);

       // 4. Restituisce la risposta
        return  AuthenticationResponseDTO.builder()
                .token(jwtToken)
                .build();
    }
}
