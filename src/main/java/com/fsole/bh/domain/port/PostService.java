package com.fsole.bh.domain.port;

import com.fsole.bh.domain.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();
    Post getPostById(Long id);
    void deletePostById(Long id);
}
