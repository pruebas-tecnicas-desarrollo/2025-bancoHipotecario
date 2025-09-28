package com.fsole.bh.application.service;

import com.fsole.bh.domain.exception.InvalidRequestException;
import com.fsole.bh.domain.model.Comment;
import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.model.User;
import com.fsole.bh.domain.port.comment.CommentRepository;
import com.fsole.bh.domain.port.post.PostRepository;
import com.fsole.bh.domain.port.post.PostService;
import com.fsole.bh.domain.port.user.UserRepository;
import com.fsole.bh.infrastructure.adapter.out.jsonplaceholder.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final PostMapper postMapper;

    @Override
    public List<Post> getAllPosts() {
        log.debug("going to post repository adapter to fetch all posts...");
        List<Post> posts = postRepository.getAllPosts();

        log.debug("going to user repository adapter to fetch all posts authors...");
        List<User> users = userRepository.getAllUsers();

        log.debug("going to comments repository adapter to fetch all posts comments...");
        List<Comment> comments = commentRepository.getAllComments();

        log.info("returning merged data...");
        return postMapper.mapAllPosts(posts, users, comments);
    }

    @Override
    public Post getPostById(Long id) {
        if (id <= 0) {
            log.warn("getPostById - invalid id received: {}", id);
            throw new InvalidRequestException();
        }
        log.info("getPostById - going to repository adapter to fetch post, its author and comments...");
        log.debug("going to post repository adapter to fetch post...");
        Post post = postRepository.getPostById(id);

        log.debug("going to user repository adapter to fetch post author...");
        User user = userRepository.getUserById(post.getUserId());

        log.debug("going to comments repository adapter to fetch all post comments...");
        List<Comment> comments = commentRepository.getAllCommentsByPostId(post.getId());

        log.info("returning merged data...");
        return postMapper.mapPost(post, user, comments);
    }

    @Override
    public void deletePostById(Long id) {
        if (id <= 0) {
            log.warn("deletePostById - invalid id received: {}", id);
            throw new InvalidRequestException();
        }
        log.info("deletePostById - going to repository adapter to delete post...");
        log.debug("going to post repository adapter to delete post...");
        postRepository.deletePostById(id);
    }
}
