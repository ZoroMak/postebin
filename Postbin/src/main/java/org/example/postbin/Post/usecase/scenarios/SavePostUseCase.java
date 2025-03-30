package org.example.postbin.Post.usecase.scenarios;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.persistence.s3.usecase.upload.UploadToObjectStorage;
import org.example.postbin.Post.persistence.s3.usecase.upload.url.CreatePresignedUrl;
import org.example.postbin.Post.usecase.GenerateHash;
import org.example.postbin.Post.usecase.SavePost;
import org.example.postbin.Post.usecase.access.PostPersist;
import org.example.postbin.Post.usecase.dto.PostReaderWithHashDTO;
import org.example.postbin.Post.usecase.mapper.PostReaderWithHashMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SavePostUseCase implements SavePost {
    private final PostPersist postPersist;
    private final PostReaderWithHashMapper saveMapper;
    private final GenerateHash generateHash;
    private final UploadToObjectStorage uploadToCloudStorage;
    private final CreatePresignedUrl createPresignedGetUrl;

    @Override
    public Optional<PostReaderWithHashDTO> execute(Post post) {
        if (post == null) {
            return Optional.empty();
        }

        if (post.getDescription().isEmpty()) {
            return Optional.empty();
        }

        post.setCreatedAt(LocalDateTime.now());
        post.setHashUrl(generateHash.execute());

        Optional<Post> savedPost = postPersist.save(post);

        uploadToCloudStorage.execute(post);

        if (savedPost.isEmpty())
            return Optional.empty();

        long activeDays = ChronoUnit.DAYS.between(post.getCreatedAt(), post.getDeletedAfterSomeDays());
        System.out.println(activeDays);
        savedPost.get().setUrl(createPresignedGetUrl.execute(post.getHashUrl(), activeDays));

        return Optional.ofNullable(saveMapper.map(post));
    }
}
