package com.example.progettouno.controller;

import com.example.progettouno.dto.UtenteDTO;
import com.example.progettouno.entity.Utente;
import com.example.progettouno.service.UtenteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {

    private final UtenteService utenteService;
    private final ModelMapper modelMapper;

    @Autowired
    public UtenteController(UtenteService utenteService, ModelMapper modelMapper) {
        this.utenteService = utenteService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UtenteDTO> createUtente(@RequestBody Utente utente) {
        utenteService.createUtente(utente);
        return new ResponseEntity<>(modelMapper.map(utente, UtenteDTO.class), HttpStatus.CREATED);
    }

    //Recupera Tutti gli utenti
    @GetMapping
   public ResponseEntity<List<UtenteDTO>> getAllUtenti() {
    return new ResponseEntity<>(utenteService.getAllUtenti(), HttpStatus.OK);
    }

}
