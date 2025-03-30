package org.example.postbin.auth.contoller;

import lombok.RequiredArgsConstructor;
import org.example.postbin.auth.ApplicationUser;
import org.example.postbin.auth.dto.*;
import org.example.postbin.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/regist")
    public ApplicationUser registerUser(@RequestBody RegisterRequest request) {

        return authenticationService.registerUser(request.getUsername(), request.getPassword());
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequest){
        LoginResponseDTO loginUser = authenticationService.loginUser(loginRequest);
        if (loginUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.ok(loginUser);
    }

    @PostMapping(value = "/token")
    public ResponseEntity<TokenResponseDTO> loginUser(@RequestBody TokenRequestDTO tokenRequestDTO){
        TokenResponseDTO response = authenticationService.giveToken(tokenRequestDTO);
        if (response.getTokenJWT().isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

        return ResponseEntity.ok(response);
    }
}
