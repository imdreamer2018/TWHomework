package com.thoughtworks.homework.controller;

import com.thoughtworks.homework.dto.BaseResponse;
import com.thoughtworks.homework.dto.PostResponse;
import com.thoughtworks.homework.entity.Posts;
import com.thoughtworks.homework.service.PostService;
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
@Api(tags = "PostController")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(path = "/posts")
    @ResponseBody
    public PostResponse<Iterable<Posts>> getAllPosts(
            @PageableDefault(value = 15, sort = { "timestamp" }, direction = Sort.Direction.DESC) Pageable pageable){
        return postService.getAllPosts(pageable);
    }

    @GetMapping(path = "/posts/{postId}")
    @ResponseBody
    public PostResponse<Posts> getPost(
            @PathVariable(value = "postId") Integer postId){
        return postService.findPost(postId);
    }

    @PostMapping(path = "/posts")
    @ResponseBody
    public PostResponse<Posts> createPost(
            @RequestBody Posts posts){
        return postService.createPost(posts);
    }

    @PostMapping(path = "/postsByFaker")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public BaseResponse createPostsByFaker(int postsNumber){
        return postService.createPostsByFaker(postsNumber);
    }

    @PutMapping(path = "/posts/{postId}")
    @ResponseBody
    public PostResponse<Posts> updatePost(
            @PathVariable(value = "postId") Integer postId,
            @RequestBody Posts posts){
        return postService.updatePost(postId,posts);
    }

    @ApiOperation(value = "删除文章",notes = "只有作者和管理员可以删除")
    @DeleteMapping(path = "/posts/{postId}")
    @ResponseBody
    public PostResponse<Posts> deletePost(@PathVariable(value = "postId") Integer postId){
        return postService.deletePost(postId);
    }

}
