package com.thoughtworks.homework.controller;

import com.thoughtworks.homework.dto.PostResponse;
import com.thoughtworks.homework.entity.Post;
import com.thoughtworks.homework.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping(path = "/posts")
    @ResponseBody
    public PostResponse<Iterable<Post>> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping(path = "/post")
    @ResponseBody
    public PostResponse<Post> getPost(@RequestParam Integer id){
        return postService.findPost(id);
    }

    @PostMapping(path = "/post")
    @ResponseBody
    public PostResponse<Post> createPost(@RequestBody Post post){
        return postService.newPost(post);
    }

    @PutMapping(path = "/post")
    @ResponseBody
    public PostResponse<Post> updatePost(@RequestBody Post post){
        return postService.updatePost(post);
    }

    @DeleteMapping(path = "/post")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public PostResponse<Post> deletePost(@RequestParam Integer id){
        return postService.deletePost(id);
    }

}
