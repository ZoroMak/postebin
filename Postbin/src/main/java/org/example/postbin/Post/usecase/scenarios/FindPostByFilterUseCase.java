package org.example.postbin.Post.usecase.scenarios;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.usecase.FindPostByFilter;
import org.example.postbin.Post.usecase.access.PostExtractor;
import org.example.postbin.Post.usecase.dto.ListPostReaderDTO;
import org.example.postbin.Post.usecase.mapper.ListPostReaderMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindPostByFilterUseCase implements FindPostByFilter {
    private final PostExtractor postExtractor;
    private final ListPostReaderMapper postReaderMapper;

    @Override
    public List<ListPostReaderDTO> execute(PostFilter filter, Page page) {
        return postExtractor.findPostByFilter(filter, page).stream()
                .map(postReaderMapper::map)
                .toList();
    }

    @Override
    public Long getTotalCount(PostFilter filter) {
        return postExtractor.count(filter);
    }
}
