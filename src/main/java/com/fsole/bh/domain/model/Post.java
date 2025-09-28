package com.fsole.bh.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String body;
    private User author;
    private List<Comment> comments;
}
