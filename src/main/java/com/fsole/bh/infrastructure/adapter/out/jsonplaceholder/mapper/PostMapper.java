package com.fsole.bh.infrastructure.adapter.out.jsonplaceholder.mapper;

import com.fsole.bh.domain.model.Comment;
import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    public List<Post> mapAllPosts(List<Post> posts, List<User> users, List<Comment> comments) {
        Map<Long, User> usersById = users.stream()
                .collect(Collectors.toMap(User::getId, u -> u));

        Map<Long, List<Comment>> commentsByPostId = comments.stream()
                .collect(Collectors.groupingBy(Comment::getPostId));

        return posts.stream()
                .map(post -> {
                    User user = usersById.get(post.getUserId());
                    post.setAuthor(user);
                    post.setComments(commentsByPostId.getOrDefault(post.getId(), List.of()));
                    return post;
                })
                .toList();
    }

    public Post mapPost(Post post, User user, List<Comment> comments) {
        post.setAuthor(user);
        post.setComments(comments);
        return post;
    }
}

