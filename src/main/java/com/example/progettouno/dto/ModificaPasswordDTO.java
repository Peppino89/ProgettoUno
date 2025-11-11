package com.example.progettouno.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModificaPasswordDTO {

    @NotBlank(message = "La vecchia password è obbligatoria.")
    private String oldPassword;


    @NotBlank(message = "La nuova password è obbligatoria.")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "La password deve avere almeno 8 caratteri, una maiuscola, una minuscola, un numero e un simbolo speciale"
    )
    private String newPassword;


    @NotBlank(message = "La conferma della nuova password è obbligatoria.")
    private String confirmNewPassword;
}
