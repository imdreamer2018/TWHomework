package com.thoughtworks.homework.controller;

import com.thoughtworks.homework.dto.BaseResponse;
import com.thoughtworks.homework.dto.CommentResponse;
import com.thoughtworks.homework.entity.Comments;
import com.thoughtworks.homework.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api")
@Api(tags = "CommentController")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping(path = "/comments")
    @ResponseBody
    public CommentResponse<Iterable<Comments>> getAllComments(
            @PageableDefault(value = 15, sort = { "timestamp" }, direction = Sort.Direction.DESC) Pageable pageable){
        return commentService.getAllComments(pageable);
    }

    @PostMapping(path = "/commentsByFaker")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse createCommentsByFaker(int commentsNumber){
        return commentService.createCommentsByFaker(commentsNumber);
    }


    @GetMapping(path = "/posts/{postId}/comments")
    @ResponseBody
    public CommentResponse<Iterable<Comments>> getComment(
            @PageableDefault(value = 15, sort = { "timestamp" }, direction = Sort.Direction.DESC) Pageable pageable,
            @PathVariable (value = "postId") Integer postId ){
        return commentService.findComment(postId, pageable);
    }

    @PostMapping(path = "/posts/{postId}/comments")
    @ResponseBody
    public CommentResponse<Comments> createComment(
            @PathVariable (value = "postId") Integer postId,
            @RequestBody Comments comments){
        return commentService.createComment(postId,comments);
    }

    @PutMapping(path = "/posts/{postId}/comments/{commentId}")
    @ResponseBody
    public CommentResponse<Comments> updateComment(
            @PathVariable (value = "postId") Integer postId,
            @PathVariable (value = "commentId") Integer commentId,
            @RequestBody Comments comments){
        return commentService.updateComment(postId, commentId, comments);
    }

    @ApiOperation(value = "删除评论",notes = "只有作者和管理员可以删除")
    @DeleteMapping(path = "/posts/{postId}/comments/{commentId}")
    @ResponseBody
    public CommentResponse<Comments> deleteComment(
            @PathVariable (value = "postId") Integer postId,
            @PathVariable (value = "commentId") Integer commentId){
        return commentService.deleteComment(postId, commentId);
    }

}
