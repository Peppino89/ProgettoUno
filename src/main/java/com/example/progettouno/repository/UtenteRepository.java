package com.example.progettouno.repository;

import com.example.progettouno.entity.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UtenteRepository extends MongoRepository<Utente, String> {

    // Ricerca per nome
    Utente findByName(String name);

    // Ricerca per nome ed email
    Optional<Utente> findByEmail(String email);

}


