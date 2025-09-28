package com.fsole.bh.infrastructure.adapter.in.rest;
import com.fsole.bh.domain.model.Post;
import com.fsole.bh.domain.port.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Posts", description = "Operations related to posts")
public class PostController {
    private final PostService postService;

    @Operation(summary = "Get all posts", description = "Fetch all posts with their authors and comments")
    @ApiResponse(responseCode = "200", description = "List of posts returned successfully")
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        log.info("getAllPosts - request received");
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @Operation(summary = "Get post by ID", description = "Fetch a single post by its unique identifier, including author and comments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        log.info("getPostById - request received with post id: '{}'", id);
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(summary = "Delete post by ID", description = "Delete a post by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content)
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        log.info("deletePostById - request received with post id: '{}'", id);
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }
}
