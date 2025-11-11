package com.example.progettouno.service.impl;

import com.example.progettouno.dto.ModificaPasswordDTO;
import com.example.progettouno.dto.UtenteDTO;
import com.example.progettouno.entity.Utente;
import com.example.progettouno.exception.UserAlreadyExistsException;
import com.example.progettouno.exception.UserNotFoundException;
import com.example.progettouno.repository.UtenteRepository;
import com.example.progettouno.service.UtenteService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder ;

    @Autowired
    public UtenteServiceImpl(UtenteRepository repository, ModelMapper modelMapper,
                             PasswordEncoder passwordEncoder) {
        this.utenteRepository = repository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UtenteDTO createUtente(Utente utente) {
        // Verifica se l'utente esiste già tramite email
        Utente utenteEsistente = utenteRepository.findByEmail(utente.getEmail());
        if (utenteEsistente != null) {
            throw new UserAlreadyExistsException("Utente già esistente con questa email");
        }

        // Codifica la password
        String hashedPassword = passwordEncoder.encode(utente.getPasswordHashed());
        utente.setPasswordHashed(hashedPassword);

        // Salva l'utente nel database
        utenteRepository.save(utente);

        // Mappa l'entità a DTO e ritorna
        return modelMapper.map(utente, UtenteDTO.class);
    }

    @Override
    public UtenteDTO updatePassword(String id, ModificaPasswordDTO modificaPasswordDTO) {
        // Recupera l'utente, lancia eccezione se non trovato
        Utente utenteEsistente = utenteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato con id: " + id));

        // Codifica la nuova password
        String hashedPassword = passwordEncoder.encode(modificaPasswordDTO.getNewPassword());
        utenteEsistente.setPasswordHashed(hashedPassword);

        // Aggiorna nel database
        utenteRepository.save(utenteEsistente);

        // Ritorna DTO
        return modelMapper.map(utenteEsistente, UtenteDTO.class);
    }

    @Override
    public UtenteDTO deleteUtente(String id) {
        // Trova l'utente, lancia eccezione se non trovato
        Utente utenteEsistente = utenteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato con id: " + id));

        // Elimina l'utente
        utenteRepository.delete(utenteEsistente);

        // Ritorna DTO dell'utente eliminato
        return modelMapper.map(utenteEsistente, UtenteDTO.class);
    }

    @Override
    public UtenteDTO getUtenteById(String id) {
        // Trova l'utente, lancia eccezione se non trovato
        Utente utente = utenteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato con id: " + id));
        return modelMapper.map(utente, UtenteDTO.class);
    }

    @Override
    public List<UtenteDTO> getAllUtenti() {
        // Recupera tutti gli utenti
        List<Utente> utenti = utenteRepository.findAll();

        // Mappa la lista di entità a lista di DTO
        Type listType = new TypeToken<List<UtenteDTO>>() {}.getType();
        return modelMapper.map(utenti, listType);
    }
}