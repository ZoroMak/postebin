package org.example.postbin.Post.persistence.web;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.persistence.web.mapper.FromWebPostMapper;
import org.example.postbin.Post.persistence.web.ratelimited.RateLimited;
import org.example.postbin.Post.usecase.FindPostByFilter;
import org.example.postbin.Post.usecase.GetPostByHashUrl;
import org.example.postbin.Post.usecase.SavePost;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.example.postbin.Post.usecase.dto.PostReaderWithHashDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins = "http://localhost:3000/",
        allowedHeaders = {"Authorization", "Content-Type"})
@RequiredArgsConstructor
public class PostController {
    private final GetPostByHashUrl getPostByHashUrl;
    private final FindPostByFilter postsFilter;
    private final SavePost savePost;
    private final FromWebPostMapper fromWebPostMapper;

    @GetMapping("/{hashUrl}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "429", description = "Too Many Requests"),
            @ApiResponse(responseCode = "404", description = "Post with hash not found")
    })
    @RateLimited
    public ResponseEntity<PostReaderDTO> getPostByHashUrl(@PathVariable String hashUrl, Principal principal) {
        if (isBase64Loose(hashUrl)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new PostReaderDTO(0L, "WARN", "Invalid request", "", LocalDateTime.now()
            ));
        }
        Optional<PostReaderDTO> post = getPostByHashUrl.execute(hashUrl);
        return post.map(postReaderDTO -> ResponseEntity.status(HttpStatus.OK)
                .body(postReaderDTO))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new PostReaderDTO(0L, "WARN", "not found", "", LocalDateTime.now())
                        ));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gets posts"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
    })
    public ResponseEntity<FilteringData> getPosts(Page page, PostFilter filter, Principal principal) {
        if (page.getSize() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new FilteringData(List.of(), 0)
            );

        FilteringData data = new FilteringData(
                postsFilter.execute(filter, page),
                postsFilter.getTotalCount(filter)
        );
        return ResponseEntity.status(HttpStatus.OK)
                .body(data);
    }

    @PostMapping
    @RateLimited
    public ResponseEntity<PostReaderWithHashDTO> savePost(@RequestBody WebPost post, Principal principal) {
        return savePost.execute(fromWebPostMapper.map(post))
                    .map(postReaderWithHashDTO -> ResponseEntity.status(HttpStatus.CREATED).body(postReaderWithHashDTO))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    public static boolean isBase64Loose(String s) {
        return s != null && s.matches("[A-Za-z0-9+/=]+");
    }
}
