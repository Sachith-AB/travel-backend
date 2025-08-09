package com.travel.travel.Repository;

import com.travel.travel.Models.Comment;
import com.travel.travel.Models.Comment.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserId(Long userId);
    List<Comment> findByRoleAndRoleId(RoleType role, Long roleId);
}
