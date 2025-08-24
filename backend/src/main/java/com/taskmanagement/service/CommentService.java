package com.taskmanagement.service;

import com.taskmanagement.dto.CommentCreateRequest;
import com.taskmanagement.entity.Comment;
import com.taskmanagement.entity.User;
import com.taskmanagement.exception.EntityNotFoundException;
import com.taskmanagement.exception.UnauthorizedException;
import com.taskmanagement.repository.CommentRepository;
import com.taskmanagement.repository.TaskRepository;
import com.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment createComment(CommentCreateRequest request, Long authorId) {
        // Validate task exists
        if (!taskRepository.existsById(request.getTaskId())) {
            throw new EntityNotFoundException("Task not found with ID: " + request.getTaskId());
        }

        // Validate author exists
        if (!userRepository.existsById(authorId)) {
            throw new EntityNotFoundException("Author not found with ID: " + authorId);
        }

        Comment comment = new Comment(request.getContent(), request.getTaskId(), authorId);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByTaskId(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Task not found with ID: " + taskId);
        }
        return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId);
    }

    public Comment updateComment(Long commentId, String content, Long userId) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        if (!existing.getAuthorId().equals(userId)) {
            throw new UnauthorizedException("Not authorized to update this comment");
        }

        existing.setContent(content);
        return commentRepository.save(existing);
    }

    public void deleteComment(Long commentId, Long userId, User requester) {
        Comment existing = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with ID: " + commentId));

        boolean isAuthor = existing.getAuthorId().equals(userId);
        boolean isAdmin = requester != null && requester.getRole() != null && requester.getRole().name().equals("ADMIN");

        if (!isAuthor && !isAdmin) {
            throw new UnauthorizedException("Not authorized to delete this comment");
        }

        commentRepository.deleteById(commentId);
    }
}
