package org.example.postbin.Post.usecase.scenarios;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Post;
import org.example.postbin.Post.persistence.s3.usecase.download.DownloadFromCloudStorage;
import org.example.postbin.Post.usecase.GetPostByHashUrl;
import org.example.postbin.Post.usecase.access.PostExtractor;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;
import org.example.postbin.Post.usecase.mapper.PostReaderMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindPostByHashUrlUseCase implements GetPostByHashUrl {
    private final PostExtractor postExtractor;
    private final PostReaderMapper postReaderMapper;
    private final DownloadFromCloudStorage downloadFromCloudStorage;

    @Override
    public Optional<PostReaderDTO> execute(String hashUrl) {
        Optional<Post> post = postExtractor.getPostByHashUrl(hashUrl);
        post.ifPresent(value -> value.setDescription(downloadFromCloudStorage.execute(hashUrl)));
        return post
                .map(postReaderMapper::map);
    }
}
