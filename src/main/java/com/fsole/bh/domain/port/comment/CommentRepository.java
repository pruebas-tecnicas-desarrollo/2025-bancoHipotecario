package com.fsole.bh.domain.port.comment;

import com.fsole.bh.domain.model.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getAllComments();
    List<Comment> getAllCommentsByPostId(Long id);
}
