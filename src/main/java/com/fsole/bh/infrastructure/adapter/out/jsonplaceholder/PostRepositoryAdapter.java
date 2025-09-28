package com.fsole.bh.infrastructure.adapter.out.jsonplaceholder;

import com.fsole.bh.domain.exception.PostNotFoundException;
import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.port.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
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
        log.info("getAllPosts - fetching all posts");
        Post[] posts = restTemplate.getForObject(BASE_URL, Post[].class);
        return Arrays.asList(posts);
    }

    @Override
    @Cacheable(value = "posts", key = "#id")
    public Post getPostById(Long id) {
        log.info("getPostById - fetching post");
        String url = BASE_URL + id;

        try {
            return restTemplate.getForObject(url, Post.class);
        } catch (HttpClientErrorException.NotFound ex) {
            log.error("Post with id {} not found", id);
            throw new PostNotFoundException(id);
        }
    }

    @Override
    @CacheEvict(value = "posts", key = "#id")
    public void deletePostById(Long id) {
        log.info("deletePostById - deleting post");
        String url = BASE_URL + id;

        try {
            restTemplate.delete(url, Post.class);
        } catch (HttpClientErrorException.NotFound ex) {
            log.error("Post with id {} not found", id);
            throw new PostNotFoundException(id);
        }
        log.info("deletePostById - post deleted successfully");
    }
}
