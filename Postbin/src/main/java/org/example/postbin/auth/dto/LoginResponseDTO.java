package org.example.postbin.auth.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.postbin.auth.ApplicationUser;


@Getter
@Setter
public class LoginResponseDTO {
    private String accessCode;

    public LoginResponseDTO(String accessCode){
        this.accessCode = accessCode;
    }
}
