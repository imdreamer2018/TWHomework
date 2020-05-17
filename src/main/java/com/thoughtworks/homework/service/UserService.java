package com.thoughtworks.homework.service;

import com.thoughtworks.homework.dto.PostResponse;
import com.thoughtworks.homework.dto.UserResponse;
import com.thoughtworks.homework.entity.Posts;
import com.thoughtworks.homework.entity.Users;
import com.thoughtworks.homework.exception.AuthorizationException;
import com.thoughtworks.homework.exception.BaseUserException;
import com.thoughtworks.homework.repository.PostRepository;
import com.thoughtworks.homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CurrentUserInfoService currentUserInfoService;

    private UserResponse<Users> generateUserRes(int code, String message, Users user){
        UserResponse<Users> u = new UserResponse<>();
        u.setCode(code);
        u.setMessage(message);
        u.setData(user);
        return u;
    }

    public UserResponse<Iterable<Users>> getAllUsers(Pageable pageable){
        UserResponse<Iterable<Users>> u= new UserResponse<>();
        u.setCode(200);
        u.setMessage("数据获取成功！");
        u.setData(userRepository.findAll(pageable));
        return u;
    }

    public UserResponse<Users> findUserByID (Integer userId) {
        Optional<Users> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new BaseUserException("用户不存在");
        }
        return generateUserRes(200,"查找成功！",user.get());
    }

    public UserResponse<Users> updateUser(Integer userId, Users users) {
        Users u = currentUserInfoService.getUserInfo();
        if (!userId.equals(u.getId())){
            throw new AuthorizationException("您没有权限");
        }
        u.setUsername(users.getUsername());
        u.setAge(users.getAge());
        u.setGender(users.getGender());
        userRepository.save(u);
        return generateUserRes(200,"用户名修改成功！",u);
    }

    public UserResponse<Users> deleteUser(Integer userId) {
        Optional<Users> u = userRepository.findById(userId);
        if(u.isPresent()){
            userRepository.deleteById(userId);
            return generateUserRes(200,"该用户信息已删除！",u.get());
        }
        throw new BaseUserException("该用户不存在！");
    }

    public PostResponse<Iterable<Posts>> getUserAllPosts(
            @PageableDefault(value = 15, sort = { "timestamp" }, direction = Sort.Direction.DESC)
            Integer userId, Pageable pageable) {
        Optional<Users> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new BaseUserException("用户不存在");
        }
        PostResponse<Iterable<Posts>> postResponse= new PostResponse<>();
        postResponse.setCode(200);
        postResponse.setMessage("文章数据获取成功！");
        postResponse.setData(postRepository.findByUsersId(userId,pageable));
        return postResponse;

    }
}
