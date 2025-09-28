package com.fsole.bh.infrastructure.adapter.out.jsonplaceholder;

import com.fsole.bh.domain.exception.ExternalServiceTimeoutException;
import com.fsole.bh.domain.exception.NotFoundException;
import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.port.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryAdapter implements PostRepository {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/posts/";

    @Override
    @Cacheable(value = "posts", key = "'all'")
    public List<Post> getAllPosts() {
        log.info("trying to fetch all posts...");

        try{
            Post[] posts = restTemplate.getForObject(BASE_URL, Post[].class);
            log.debug("all posts were fetched successfully");
            return Arrays.asList(posts);
        } catch (ResourceAccessException ex) {
            log.error("timeout while trying to fetch all posts");
            throw new ExternalServiceTimeoutException(BASE_URL);
        }
    }

    @Override
    @Cacheable(value = "posts", key = "#id")
    public Post getPostById(Long id) {
        log.info("trying to fetch post...");
        String url = BASE_URL + id;

        try {
            Post post = restTemplate.getForObject(url, Post.class);
            log.debug("the required post was fetched successfully");
            return post;
        } catch (HttpClientErrorException.NotFound ex) {
            String error = "the required post was not found";
            log.error(error);
            throw new NotFoundException(error);
        } catch (ResourceAccessException ex) {
            log.error("timeout while trying to fetch the required post");
            throw new ExternalServiceTimeoutException(url);
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "posts", key = "#id", beforeInvocation = true),
            // this forces fetching fresh data on the next getAllPosts call, preventing inconsistencies
            @CacheEvict(value = "posts", key = "'all'", beforeInvocation = true)
    })
    public void deletePostById(Long id) {
        log.info("trying to delete post...");
        String url = BASE_URL + id;

        try {
            restTemplate.delete(url, Post.class);
            log.debug("the required post was deleted successfully");
        } catch (HttpClientErrorException.NotFound ex) {
            String error = "the required post was not found";
            log.error(error);
            throw new NotFoundException(error);
        } catch (ResourceAccessException ex) {
            log.error("timeout while trying to delete the required post");
            throw new ExternalServiceTimeoutException(url);
        }
    }
}
