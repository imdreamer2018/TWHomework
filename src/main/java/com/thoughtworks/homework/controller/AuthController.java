package com.thoughtworks.homework.controller;

import com.thoughtworks.homework.dto.AuthorizationResponse;
import com.thoughtworks.homework.dto.UserResponse;
import com.thoughtworks.homework.entity.User;
import com.thoughtworks.homework.service.AuthorizationService;
import com.thoughtworks.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping(path = "/")
    public @ResponseBody String index(){
        System.out.println("hello,world");
        return "Hello World!";
    }

    @PostMapping(path = "/register")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse<User> addNewUser(@RequestBody User user,@RequestParam String registerCode) {
        return userService.creatUser(user,registerCode);
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public AuthorizationResponse login(@RequestParam String email, @RequestParam String password) {
        return authorizationService.login(email,password);
    }
}
