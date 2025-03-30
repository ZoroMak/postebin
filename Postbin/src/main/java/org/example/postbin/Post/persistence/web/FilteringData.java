package org.example.postbin.Post.persistence.web;

import lombok.Builder;
import lombok.Value;
import org.example.postbin.Post.usecase.dto.ListPostReaderDTO;

import java.util.List;

@Value
public class FilteringData {
    List<ListPostReaderDTO> objects;
    long totalCount;
}
