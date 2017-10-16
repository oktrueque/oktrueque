package com.oktrueque.controller;

import com.oktrueque.model.Comment;
import com.oktrueque.model.User;
import com.oktrueque.service.CommentService;
import com.oktrueque.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class CommentsController {

    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public CommentsController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{username}/comments")
    public ResponseEntity<Page<Comment>> getCommentsUser(@RequestParam int page, @PathVariable String username){
        User user = userService.getUserByUsername(username);
        Page<Comment> comments =  commentService.getCommentsByUserTargetId(user.getId(), new PageRequest(page, 5));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/comments/paginated")
    public ResponseEntity<Page<Comment>> getCommentsProfile(@RequestParam int page, Principal principal){
        User user = userService.getUserByUsername(principal.getName());
        Page<Comment> comments =  commentService.getCommentsByUserTargetId(user.getId(), new PageRequest(page, 5));
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
