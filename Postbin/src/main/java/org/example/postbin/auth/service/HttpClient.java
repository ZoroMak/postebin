package org.example.postbin.auth.service;

import lombok.RequiredArgsConstructor;
import org.example.postbin.auth.ApplicationUser;
import org.example.postbin.auth.dto.TokenRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class HttpClient {
    private final RestTemplate restTemplate;
    public TokenRequestDTO sendPostRequest(ApplicationUser user, String url) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("accessCode", user.getAccessCode());

        try {
            // Выполняем POST-запрос
            ResponseEntity<TokenRequestDTO> response = restTemplate.exchange(
                    url, HttpMethod.POST,
                    new HttpEntity<>(requestBody),
                    TokenRequestDTO.class
            );

            // Проверяем статус ответа
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                return null;
            }
        } catch (RestClientException e) {
            return null;
        }
    }
}
