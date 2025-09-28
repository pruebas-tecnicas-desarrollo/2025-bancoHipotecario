package com.fsole.bh.infrastructure.adapter.out.jsonplaceholder;

import com.fsole.bh.domain.exception.ExternalServiceTimeoutException;
import com.fsole.bh.domain.model.Comment;
import com.fsole.bh.domain.port.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.ResourceAccessException;
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
        log.info("getAllComments - fetching all comments in all posts...");

        try{
            Comment[] comments = restTemplate.getForObject(BASE_URL, Comment[].class);
            log.debug("getAllComments - all comments in all posts were fetched successfully");
            return Arrays.asList(comments);
        } catch (ResourceAccessException ex) {
            log.error("getAllComments - timeout while trying to fetch all comments in all posts");
            throw new ExternalServiceTimeoutException(BASE_URL);
        }
    }

    @Override
    public List<Comment> getAllCommentsByPostId(Long postId) {
        log.info("getAllCommentsByPostId - fetching all comments in post id '{}'...", postId);
        String url = BASE_URL + "?postId=" + postId;

        try{
            Comment[] comments = restTemplate.getForObject(url, Comment[].class);
            log.debug("getAllCommentsByPostId - all comments in this post were fetched successfully");
            return Arrays.asList(comments);
        } catch (ResourceAccessException ex) {
            log.error("getAllCommentsByPostId - timeout while trying to fetch all comments in this post");
            throw new ExternalServiceTimeoutException(BASE_URL);
        }
    }
}
