package org.example.postbin.Post.usecase;

import org.example.postbin.Post.domain.Page;
import org.example.postbin.Post.domain.filter.PostFilter;
import org.example.postbin.Post.persistence.db.utils.QPredicates;
import org.example.postbin.Post.usecase.dto.ListPostReaderDTO;
import org.example.postbin.Post.usecase.dto.PostReaderDTO;

import java.util.List;

import static org.example.postbin.Post.persistence.db.model.QPostData.postData;

public interface FindPostByFilter{
    List<ListPostReaderDTO> execute (PostFilter filter, Page page);

    Long getTotalCount(PostFilter filter);
}
