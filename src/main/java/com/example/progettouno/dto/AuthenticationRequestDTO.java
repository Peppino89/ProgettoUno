package com.example.progettouno.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthenticationRequestDTO {

    @NotBlank(message = "L'email non può essere vuota")
    @Email(message = "Formato non valido")
    private String email;

    @NotBlank(message = "La password non può essere vuota")
    private String password;
}
