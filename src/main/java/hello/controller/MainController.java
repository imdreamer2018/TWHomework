package hello.controller;

import hello.DTO.UserDTO;
import hello.Exception.BaseUserException;
import hello.Exception.UserException;
import hello.entity.User;
import hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(path="/api")
public class MainController {

    @Autowired

    private UserRepository userRepository;

    @GetMapping(path = "/")
    public @ResponseBody String index(){
        System.out.println("hello,world");
        return "Hello World!";
    }

    @PostMapping(path = "/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO<User> addNewUser(@RequestParam String username,
                                           @RequestParam int age,
                                           @RequestParam String gender) throws BaseUserException {
        List<User> u = userRepository.findUser(username);
        if (!u.isEmpty()) {
            throw new BaseUserException("用户已存在");
        }
        User n = new User();
        n.setUsername(username);
        n.setAge(age);
        n.setGender(gender);
        userRepository.save(n);
        UserDTO<User> u1 = new UserDTO<User>();
        u1.setCode(201);
        u1.setData(n);
        u1.setMessage("添加用户成功！");
        return u1;
    }

    @GetMapping(path = "/users")
    @ResponseBody
    public UserDTO<Iterable<User>> getAllUsers(){

        UserDTO<Iterable<User>> u= new UserDTO<Iterable<User>>();
        u.setCode(200);
        u.setData(userRepository.findAll());
        u.setMessage("数据获取成功！");
        return u;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="/user")
    @ResponseBody
    public UserDTO<User> getUser(@RequestParam int id) throws BaseUserException {
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

    @PutMapping(path="/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public  UserDTO<User> updateUser(@RequestParam int id, @RequestParam String username) throws BaseUserException {
        Optional<User> u = userRepository.findById(id);
        if(u.isPresent()){
            if(u.get().getUsername().equals(username))
                throw new BaseUserException("该用户名无需修改！");
            else {
                List <User> list = userRepository.findUser(username);
                if(list.isEmpty()) {
                    u.get().setUsername(username);
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

    @DeleteMapping(path = "/user")
    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDTO<User> deleteUser(@RequestParam int id) throws BaseUserException {
        Optional<User> u=userRepository.findById(id);
        if(u.isPresent()){
            userRepository.deleteById(id);
            UserDTO<User> u1 = new UserDTO<User>();
            u1.setCode(200);
            u1.setData(u.get());
            u1.setMessage("该用户信息已删除！");
            return u1;
         }
        throw new BaseUserException("该用户不存在！");
    }

    @GetMapping(path = "/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public  UserDTO<User> json(@RequestParam int id) throws UserException{
        Optional<User> u=userRepository.findById(id);
        if(u.isPresent()) {
            UserDTO<User> s = new UserDTO<User>();
            s.setCode(200);
            s.setData(u.get());
            s.setMessage("查找成功");
            return s;
        }
        throw new UserException("抛出异常");
    }
}
