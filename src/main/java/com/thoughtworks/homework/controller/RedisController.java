package com.thoughtworks.homework.controller;

import com.thoughtworks.homework.entity.User;
import com.thoughtworks.homework.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path="/api")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping(path = "/redisUser")
    @ResponseBody
    public String saveUser(@RequestBody User user){
        redisService.set(user.getUsername(),user);
        return "success";
    }

    @GetMapping(path = "/redisUser")
    @ResponseBody
    public Object getUser(@RequestParam String username){
        return redisService.get(username);
    }

    @RequestMapping(path = "/clearRedis")
    @ResponseBody
    public String clearRedis(){
        redisService.clearRedis();
        return "clear success!";
    }
}
