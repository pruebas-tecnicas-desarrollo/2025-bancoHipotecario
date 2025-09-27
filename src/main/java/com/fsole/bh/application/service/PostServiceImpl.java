package com.fsole.bh.application.service;

import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.port.post.PostRepository;
import com.fsole.bh.domain.port.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        log.info("getAllPosts - going to repository adapter to fetch all posts");
        return postRepository.getAllPosts();
    }

    @Override
    public Post getPostById(Long id) {
        log.info("getPostById - going to repository adapter  to fetch post");
        return postRepository.getPostById(id);
    }

    @Override
    public void deletePostById(Long id) {
        log.info("deletePostById - going to repository adapter to delete post");
        postRepository.deletePostById(id);
    }
}
