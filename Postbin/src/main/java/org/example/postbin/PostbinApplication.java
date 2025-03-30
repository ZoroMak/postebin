package org.example.postbin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PostbinApplication {
    public static void main(String[] args) {
        SpringApplication.run(PostbinApplication.class, args);
    }
}
