package com.example.progettouno.repository;

import com.example.progettouno.entity.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtenteRepository extends MongoRepository<Utente, String> {

    // Ricerca per nome
    Utente findByName(String name);


    // Ricerca per nome ed email
    Utente findByEmail(String email);

}


