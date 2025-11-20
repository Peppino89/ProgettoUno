package com.example.progettouno.service.impl;

import com.example.progettouno.dto.ModificaPasswordDTO;
import com.example.progettouno.dto.UtenteDTO;
import com.example.progettouno.entity.Utente;
import com.example.progettouno.exception.BadPasswordException;
import com.example.progettouno.exception.UserAlreadyExistsException;
import com.example.progettouno.exception.UserNotFoundException;
import com.example.progettouno.repository.UtenteRepository;
import com.example.progettouno.service.UtenteService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

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
        Optional<Utente> utenteEsistente = utenteRepository.findByEmail(utente.getEmail());
        if (utenteEsistente.isPresent()) {
            throw new UserAlreadyExistsException("Utente già esistente con questa email");
        }

        // Codifica la password
        String hashedPassword = passwordEncoder.encode(utente.getPassword());
        utente.setPassword(hashedPassword);

        // Salva l'utente nel database
        utenteRepository.save(utente);

        // Mappa l'entità a DTO e ritorna
        return modelMapper.map(utente, UtenteDTO.class);
    }

    @Override
    public UtenteDTO updatePassword(String id, ModificaPasswordDTO modificaPasswordDTO) {
        // 1. Recupera l'utente
        Utente utenteEsistente = utenteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato con id: " + id));

        // 2. 🚨 VERIFICA 1: Vecchia Password (Sicurezza)
        if (!passwordEncoder.matches(modificaPasswordDTO.getOldPassword(), utenteEsistente.getPassword())) {
            throw new BadPasswordException("La vecchia password non è corretta.");
        }

        // 3. 🚨 VERIFICA 2: Coincidenza delle nuove password (Usabilità/Validazione)
        // Questo controllo è fondamentale, dato che non è automatico tramite annotazione standard.
        if (!modificaPasswordDTO.getNewPassword().equals(modificaPasswordDTO.getConfirmNewPassword())) {

            throw new BadPasswordException("La nuova password e la sua conferma non coincidono.");
        }

        // 4. Verifica che la nuova password non sia uguale alla vecchia (Raccomandato)
        if (modificaPasswordDTO.getNewPassword().equals(modificaPasswordDTO.getOldPassword())) {
            throw new BadPasswordException("La nuova password non può essere uguale a quella vecchia.");
        }

        // 5. Codifica la nuova password
        String hashedPassword = passwordEncoder.encode(modificaPasswordDTO.getNewPassword());
        utenteEsistente.setPassword(hashedPassword);

        // 6. Aggiorna nel database
        utenteRepository.save(utenteEsistente);

        // Ritorna DTO
        return modelMapper.map(utenteEsistente, UtenteDTO.class);
    }

    @Override
    public void deleteUtente(String id) {
        // Trova l'utente, lancia eccezione se non trovato
        Utente utenteEsistente = utenteRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Utente non trovato con id: " + id));

        // Elimina l'utente
        utenteRepository.delete(utenteEsistente);

        // Ritorna DTO dell'utente eliminato
       // return modelMapper.map(utenteEsistente, UtenteDTO.class);
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