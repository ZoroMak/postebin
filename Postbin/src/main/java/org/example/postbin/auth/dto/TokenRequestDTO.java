package org.example.postbin.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDTO {
    private String username;
    private String password;
    private String accessCode;
    private String codeVerifier;
}
