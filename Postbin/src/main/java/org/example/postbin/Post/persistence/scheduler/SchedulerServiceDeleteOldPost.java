package org.example.postbin.Post.persistence.scheduler;

import lombok.RequiredArgsConstructor;
import org.example.postbin.Post.persistence.db.model.PostData;
import org.example.postbin.Post.persistence.db.repository.PostRepository;
import org.example.postbin.Post.persistence.db.utils.QPredicates;
import org.example.postbin.Post.persistence.s3.usecase.delete.DeletePostInCloudStorage;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.example.postbin.Post.persistence.db.model.QPostData.postData;

@Service
@RequiredArgsConstructor
public class SchedulerServiceDeleteOldPost implements SchedulerService{
    private final PostRepository repository;
    private final DeletePostInCloudStorage connectionToCloudStorage;
    private static final Integer BATCH_SIZE = 100;
    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleTask() {
        LocalDateTime currentDate = LocalDateTime.now();

        var predicate = QPredicates.getInstance()
                .add(currentDate, postData.deletedAfterSomeDays::before)
                .buildAnd();

        Pageable page = Pageable.ofSize(BATCH_SIZE);

        Iterable<PostData> posts;

        do {
            posts = repository.findAll(predicate, page);

            for (PostData post : repository.findAll(predicate)) {
                connectionToCloudStorage.execute(post.getHashUrl());
                repository.delete(post);
            }

        } while (posts.iterator().hasNext());
    }
}
