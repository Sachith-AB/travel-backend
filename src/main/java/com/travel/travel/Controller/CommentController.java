package com.travel.travel.Controller;

import com.travel.travel.Models.Comment;
import com.travel.travel.Models.Comment.RoleType;
import com.travel.travel.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Comment comment) {
        return ResponseEntity.ok(commentService.createComment(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            return ResponseEntity.ok(commentService.updateComment(id, comment));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.getCommentsByUserId(userId));
    }

    @GetMapping("/by-role")
    public ResponseEntity<List<Comment>> getByRoleAndRoleId(
            @RequestParam("role") RoleType role,
            @RequestParam("roleId") Long roleId) {
        return ResponseEntity.ok(commentService.getCommentsByRoleAndRoleId(role, roleId));
    }
}
