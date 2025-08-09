package com.travel.travel.Service.ServiceImpl;

import com.travel.travel.Models.Comment;
import com.travel.travel.Models.Comment.RoleType;
import com.travel.travel.Repository.CommentRepository;
import com.travel.travel.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateComment(Long id, Comment updated) throws Exception {
        Comment existing = commentRepository.findById(id)
                .orElseThrow(() -> new Exception("Comment not found with ID: " + id));
        existing.setContent(updated.getContent());
        existing.setRating(updated.getRating());
        return commentRepository.save(existing);
    }

    @Override
    public void deleteComment(Long id) throws Exception {
        if (!commentRepository.existsById(id)) {
            throw new Exception("Comment not found");
        }
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    @Override
    public List<Comment> getCommentsByRoleAndRoleId(RoleType role, Long roleId) {
        return commentRepository.findByRoleAndRoleId(role, roleId);
    }
}
