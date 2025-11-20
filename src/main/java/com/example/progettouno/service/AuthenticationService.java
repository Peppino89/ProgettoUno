package com.example.progettouno.service;

import com.example.progettouno.dto.AuthenticationRequestDTO;
import com.example.progettouno.dto.AuthenticationResponseDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
