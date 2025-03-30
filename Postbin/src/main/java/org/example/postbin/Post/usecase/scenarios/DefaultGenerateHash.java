package org.example.postbin.Post.usecase.scenarios;

import org.example.postbin.Post.usecase.GenerateHash;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class DefaultGenerateHash implements GenerateHash {
    private static final int LENGTH_OF_HASH_URL = 8;
    @Override
    public String execute() {
        byte[] randomBytes = new byte[6];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        String base64Hash = Base64.getEncoder().encodeToString(randomBytes);

        return base64Hash.substring(0, LENGTH_OF_HASH_URL);
    }
}
