package org.example.postbin.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDTO {
    String tokenJWT;

    public TokenResponseDTO(String tokenJWT){
        this.tokenJWT = tokenJWT;
    }
}
