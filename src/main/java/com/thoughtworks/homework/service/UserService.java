package com.thoughtworks.homework.service;

import com.thoughtworks.homework.dto.UserDTO;
import com.thoughtworks.homework.entity.User;
import com.thoughtworks.homework.exception.BaseUserException;
import com.thoughtworks.homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDTO<Iterable<User>> getAllUsers(){
        UserDTO<Iterable<User>> u= new UserDTO<Iterable<User>>();
        u.setCode(200);
        u.setData(userRepository.findAll());
        u.setMessage("数据获取成功！");
        return u;
    }

    public  UserDTO<User> creatUser(User user) throws BaseUserException {
        List<User> u = userRepository.findUser(user.getUsername());
        if (!u.isEmpty()) {
            throw new BaseUserException("用户已存在");
        }
        User n = new User();
        n.setUsername(user.getUsername());
        n.setAge(user.getAge());
        n.setGender(user.getGender());
        userRepository.save(n);
        UserDTO<User> u1 = new UserDTO<>();
        u1.setCode(201);
        u1.setData(n);
        u1.setMessage("添加用户成功！");
        return u1;
    }

    public UserDTO<User> findUserById (int id) throws BaseUserException {
        Optional<User> user = userRepository.findById(id);
        //return user.orElseThrow(() -> new EntityNotFoundException("id 为 " + id + " 的用户不存在"));
        if(!user.isPresent()){
            throw new BaseUserException("用户不存在");
        }
        UserDTO<User> u =new UserDTO<User>();
        u.setCode(200);
        u.setData(user.get());
        u.setMessage("查找成功");
        return u;
    }

    public UserDTO<User> updateUserById(User user) throws BaseUserException {
        Optional<User> u = userRepository.findById(user.getId());
        if(u.isPresent()){
            if(u.get().getUsername().equals(user.getUsername()))
                throw new BaseUserException("该用户名无需修改！");
            else {
                List <User> list = userRepository.findUser(user.getUsername());
                if(list.isEmpty()) {
                    u.get().setUsername(user.getUsername());
                    userRepository.save(u.get());
                    UserDTO<User> u1 = new UserDTO<User>();
                    u1.setCode(200);
                    u1.setData(u.get());
                    u1.setMessage("用户名修改成功！");
                    return u1;
                }
                throw new BaseUserException("该用户名已存在");
            }
        }
        throw new BaseUserException("该用户不存在！");
    }

    public UserDTO<User> deleteUser(int id) throws BaseUserException {
        Optional<User> u=userRepository.findById(id);
        if(u.isPresent()){
            userRepository.deleteById(id);
            UserDTO<User> u1 = new UserDTO<>();
            u1.setCode(200);
            u1.setData(u.get());
            u1.setMessage("该用户信息已删除！");
            return u1;
        }
        throw new BaseUserException("该用户不存在！");
    }
}
