package com.fsole.bh.infrastructure.adapter.out.jsonplaceholder;

import com.fsole.bh.domain.model.Comment;
import com.fsole.bh.domain.model.User;
import com.fsole.bh.domain.port.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepositoryAdapter implements CommentRepository {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/comments/";

    @Override
    public List<Comment> getAllComments() {
        log.info("getAllComments - fetching all comments in all posts");
        Comment[] comments = restTemplate.getForObject(BASE_URL, Comment[].class);
        return Arrays.asList(comments);
    }

    @Override
    public List<Comment> getAllCommentsByPostId(Long postId) {
        log.info("getAllCommentsByPostId - fetching all comments in post id {}", postId);
        String url = BASE_URL + "?postId=" + postId;
        Comment[] comments = restTemplate.getForObject(url, Comment[].class);
        return Arrays.asList(comments);
    }
}
