package com.example.progettouno.service;

import com.example.progettouno.dto.ModificaPasswordDTO;
import com.example.progettouno.dto.UtenteDTO;
import com.example.progettouno.entity.Utente;

import java.util.List;

public interface UtenteService {
    UtenteDTO createUtente(Utente utente);
    UtenteDTO updatePassword(String id, ModificaPasswordDTO modificaPasswordDTO);
    void deleteUtente(String id);
    UtenteDTO getUtenteById(String id);
    List<UtenteDTO> getAllUtenti();
}
