package org.example.postbin.auth.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String username;
    private String password;
    private String codeChallenge;
    private String codeChallengeMethode;
}
