package org.example.postbin.Post.persistence.web.ratelimited;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final Bucket bucket;

    @Around("@annotation(RateLimited)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body(new PostReaderDTO(
                            0L,
                            "WARN",
                            "not found",
                            "",
                            LocalDateTime.now()
                    ));
        }
        return joinPoint.proceed();
    }
}
