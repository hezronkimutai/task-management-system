package com.taskmanagement.controller;

import com.taskmanagement.dto.CommentCreateRequest;
import com.taskmanagement.dto.CommentResponse;
import com.taskmanagement.entity.Comment;
import com.taskmanagement.entity.User;
import com.taskmanagement.exception.EntityNotFoundException;
import com.taskmanagement.exception.UnauthorizedException;
import com.taskmanagement.service.CommentService;
import com.taskmanagement.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
@Tag(name = "Comments", description = "Endpoints for task comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private com.taskmanagement.repository.UserRepository userRepository;

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsServiceImpl.UserPrincipal) {
            UserDetailsServiceImpl.UserPrincipal userPrincipal = (UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getId();
        }
        throw new UnauthorizedException("User not authenticated");
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsServiceImpl.UserPrincipal) {
            UserDetailsServiceImpl.UserPrincipal userPrincipal = (UserDetailsServiceImpl.UserPrincipal) authentication.getPrincipal();
            return userRepository.findById(userPrincipal.getId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found"));
        }
        throw new UnauthorizedException("User not authenticated");
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentCreateRequest request) {
        Long userId = getCurrentUserId();
        Comment created = commentService.createComment(request, userId);
        return ResponseEntity.ok(new CommentResponse(created));
    }

    @GetMapping("/task/{taskId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CommentResponse>> getCommentsForTask(@PathVariable Long taskId) {
        List<Comment> comments = commentService.getCommentsByTaskId(taskId);
        List<CommentResponse> responses = comments.stream().map(CommentResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CommentCreateRequest request) {
        Long userId = getCurrentUserId();
        Comment updated = commentService.updateComment(id, request.getContent(), userId);
        return ResponseEntity.ok(new CommentResponse(updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        User user = getCurrentUser();
        commentService.deleteComment(id, userId, user);
        return ResponseEntity.ok("{\"message\": \"Comment deleted successfully\"}");
    }
}
