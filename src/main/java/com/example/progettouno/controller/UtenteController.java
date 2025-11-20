package com.example.progettouno.controller;

import com.example.progettouno.dto.*;
import com.example.progettouno.entity.Utente;
import com.example.progettouno.service.AuthenticationService;
import com.example.progettouno.service.UtenteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService utenteService;
    private final ModelMapper modelMapper;
    private final AuthenticationService authenticationService;

    @Autowired
    public UtenteController(UtenteService utenteService, ModelMapper modelMapper, AuthenticationService authenticationService) {
        this.utenteService = utenteService;
        this.modelMapper = modelMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UtenteDTO> createUtente(@Validated @RequestBody Utente utente) {
        UtenteDTO utenteCreato = utenteService.createUtente(utente);
        return new ResponseEntity<>(utenteCreato, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticateUtente
            (@Validated @RequestBody AuthenticationRequestDTO request) {

        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

    @PatchMapping("/{id}")
    // 🚨 FIRMA AGGIORNATA: Restituisce il nuovo DTO di risposta
    public ResponseEntity<PasswordChangeResponseDTO> updatePassword(
            @PathVariable String id,
            @Validated @RequestBody ModificaPasswordDTO modificaPasswordDTO) {

        // 1. Chiama il Service e riceve l'UtenteDTO AGGIORNATO (corretto)
        UtenteDTO utenteAggiornato = utenteService.updatePassword(id, modificaPasswordDTO);

        // 2. Crea il DTO di risposta ibrido
        PasswordChangeResponseDTO responseDTO = new PasswordChangeResponseDTO(
                "La password è stata modificata con successo per l'utente con ID: " + id,
                utenteAggiornato
        );

        // 3. Restituisce il DTO ibrido con lo status 200 OK
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable String id) {
        utenteService.deleteUtente(id);

        // Risposta RESTful: 204 No Content (Operazione riuscita senza corpo)
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }


    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> getUtenteById(@PathVariable String id) {
        UtenteDTO utenteDTO = utenteService.getUtenteById(id);
        return new ResponseEntity<>(utenteDTO, HttpStatus.OK);
    }

    //Recupera Tutti gli utenti
    @GetMapping("/list")
    public ResponseEntity<List<UtenteDTO>> getAllUtenti() {
        return new ResponseEntity<>(utenteService.getAllUtenti(), HttpStatus.OK);
    }

}
