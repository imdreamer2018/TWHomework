package com.thoughtworks.homework.service;

import com.thoughtworks.homework.dto.UserResponse;
import com.thoughtworks.homework.entity.User;
import com.thoughtworks.homework.exception.BaseUserException;
import com.thoughtworks.homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse<Iterable<User>> getAllUsers(){
        UserResponse<Iterable<User>> u= new UserResponse<>();
        u.setCode(200);
        u.setData(userRepository.findAll());
        u.setMessage("数据获取成功！");
        return u;
    }

    public UserResponse<User> creatUser(User user) {
        Optional<User> u = userRepository.findUserByUsername(user.getUsername());
        if (u.isPresent()) {
            throw new BaseUserException("用户已存在");
        }
        User n = new User(user.getUsername(),user.getAge(),user.getGender());
        userRepository.save(n);
        UserResponse<User> u1 = new UserResponse<>();
        u1.setCode(201);
        u1.setData(n);
        u1.setMessage("添加用户成功！");
        return u1;
    }

    public UserResponse<User> findUserById (int id) throws BaseUserException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new BaseUserException("用户不存在");
        }
        UserResponse<User> u = new UserResponse<>();
        u.setCode(200);
        u.setData(user.get());
        u.setMessage("查找成功");
        return u;
    }

    public UserResponse<User> updateUserById(User user) throws BaseUserException {
        Optional<User> u = userRepository.findById(user.getId());
        if(u.isPresent()){
            Optional<User> list = userRepository.findUserByUsername(user.getUsername());
            if(!list.isPresent()) {
                u.get().setUsername(user.getUsername());
                userRepository.save(u.get());
                UserResponse<User> u1 = new UserResponse<>();
                u1.setCode(200);
                u1.setData(u.get());
                u1.setMessage("用户名修改成功！");
                return u1;
            }
            throw new BaseUserException("该用户名已存在");
        }
        throw new BaseUserException("该用户不存在！");
    }

    public UserResponse<User> deleteUser(int id) throws BaseUserException {
        Optional<User> u=userRepository.findById(id);
        if(u.isPresent()){
            userRepository.deleteById(id);
            UserResponse<User> u1 = new UserResponse<>();
            u1.setCode(200);
            u1.setData(u.get());
            u1.setMessage("该用户信息已删除！");
            return u1;
        }
        throw new BaseUserException("该用户不存在！");
    }
}
