package org.example.postbin.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.postbin.auth.ApplicationUser;
import org.example.postbin.auth.ApplicationUserRepository;
import org.example.postbin.auth.Role;
import org.example.postbin.auth.RoleRepository;
import org.example.postbin.auth.dto.LoginRequestDTO;
import org.example.postbin.auth.dto.LoginResponseDTO;
import org.example.postbin.auth.dto.TokenRequestDTO;
import org.example.postbin.auth.dto.TokenResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final ApplicationUserRepository applicationUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public ApplicationUser registerUser(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        return applicationUserRepository.save(new ApplicationUser(email, encodedPassword, authorities));
    }

    public LoginResponseDTO loginUser(LoginRequestDTO requestDTO) {

        try{

//            Authentication auth = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(requestDTO.getUsername(), requestDTO.getPassword())
//            );
//
//            String token = tokenService.generateJwt(auth);
            String accessCode = generateAccessCode();

            ApplicationUser user = applicationUserRepository.findByEmail(requestDTO.getUsername()).get();
            user.setAccessCode(accessCode);
            user.setCodeChallenge(requestDTO.getCodeChallenge());
//            updateTokenJWT(user, token);

            return new LoginResponseDTO(accessCode);

        }catch (AuthenticationException e) {
            return new LoginResponseDTO("");
        }
    }

    private void updateTokenJWT(ApplicationUser user, String token) {

        if (user != null) {
            user.setTokenJWT(token);
            applicationUserRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private String generateAccessCode(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] codeVerifier = new byte[32];
        secureRandom.nextBytes(codeVerifier);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier);
    }

    public TokenResponseDTO giveToken(TokenRequestDTO tokenRequestDTO){
        try{
            ApplicationUser user = applicationUserRepository.findByEmail(tokenRequestDTO.getUsername()).get();
            if (!tokenRequestDTO.getAccessCode().equals(user.getAccessCode()))
                return new TokenResponseDTO("");

            if (encodeSHA256(tokenRequestDTO.getCodeVerifier()).equals(user.getCodeChallenge()))
                return new TokenResponseDTO("");

            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(tokenRequestDTO.getUsername(), tokenRequestDTO.getPassword())
            );

            String token = tokenService.generateJwt(auth);

            updateTokenJWT(user, token);

            return new TokenResponseDTO(token);

        }catch (AuthenticationException e) {
            return new TokenResponseDTO("");
        }
    }

    public static String encodeSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return base64UrlEncode(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    private static String base64UrlEncode(byte[] bytes) {
        String base64 = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        return base64;
    }
}
