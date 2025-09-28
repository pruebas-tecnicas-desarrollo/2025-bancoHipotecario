package com.fsole.bh.infrastructure.adapter.in.rest;
import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.port.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        log.info("getAllPosts - request received");
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        log.info("getPostById - request received with post id: '{}'", id);
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        log.info("deletePostById - request received with post id: '{}'", id);
        postService.deletePostById(id);
        // changed and returning 204 No Content as required in 2.c
        return ResponseEntity.noContent().build();
    }
}
