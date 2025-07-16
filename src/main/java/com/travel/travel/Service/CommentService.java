package com.travel.travel.Service;

import com.travel.travel.Models.Comment;
import com.travel.travel.Models.Comment.RoleType;

import java.util.List;

public interface CommentService {
    Comment createComment(Comment comment);
    Comment updateComment(Long id, Comment comment) throws Exception;
    void deleteComment(Long id) throws Exception;
    List<Comment> getCommentsByUserId(Long userId);
    List<Comment> getCommentsByRoleAndRoleId(RoleType role, Long roleId);
}
