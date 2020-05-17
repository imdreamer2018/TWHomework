package com.thoughtworks.homework.controller;

import com.thoughtworks.homework.dto.PostResponse;
import com.thoughtworks.homework.dto.UserResponse;
import com.thoughtworks.homework.entity.Posts;
import com.thoughtworks.homework.entity.Users;
import com.thoughtworks.homework.service.PostService;
import com.thoughtworks.homework.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;


@Controller
@RequestMapping(path="/api")
@Api(tags = "UserController")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping(path = "/")
    public @ResponseBody String index(){
        System.out.println("hello,world");
        return "Hello World!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "/users")
    @ResponseBody
    public UserResponse<Iterable<Users>> getAllUsers(
            @PageableDefault(value = 15, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "查找用户",notes = "只有管理员才有权限")
    @GetMapping(path="/users/{userId}")
    @ResponseBody
    public UserResponse<Users> getUser(
            @PathVariable(name = "userId") Integer userId){
        return userService.findUserByID(userId);
    }

    @PutMapping(path="/users/{userId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public UserResponse<Users> updateUser(
            @PathVariable(name = "userId") Integer userId,
            @RequestBody Users users
            ) {
        return userService.updateUser(userId, users);
    }

    @ApiOperation(value = "删除用户",notes = "只有管理员才有权限")
    @DeleteMapping(path = "/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserResponse<Users> deleteUser(
            @PathVariable(name = "userId") Integer userId) {
       return userService.deleteUser(userId);
    }

    @GetMapping(path = "/users/{userId}/posts")
    @ResponseBody
    public PostResponse<Iterable<Posts>> getUserAllPosts(
            @PathVariable(name = "userId") Integer userId,
            Pageable pageable){
        return userService.getUserAllPosts(userId, pageable);
    }
}
