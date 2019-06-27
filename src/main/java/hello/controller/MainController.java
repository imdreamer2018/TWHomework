package hello.controller;

import hello.entity.User;
import hello.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(path="/user")
public class MainController {

    @Autowired

    private UserRepository userRepository;

    @GetMapping(path = "/")
    public @ResponseBody String index(){
        System.out.println("hello,world");
        return "Hello World!";
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String username,
                                           @RequestParam int age,
                                           @RequestParam String gender){
        List<User> u = userRepository.findUser(username);
        if (!u.isEmpty()) {
            for (Iterator<User> it=u.iterator();it.hasNext();) {
                System.out.println(it.next().getUsername());
            }
            return username+" is exits";
        }
        User n = new User();
        n.setUsername(username);
        n.setAge(age);
        n.setGender(gender);
        userRepository.save(n);
        return "Save successful!";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleException(EntityNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="/find")
    @ResponseBody
    public User getUser(@RequestParam int id)
    {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException("id 为 " + id + " 的用户不存在"));
    }

    @PutMapping(path="/update")
    @ResponseBody
    public  String updateUser(@RequestParam int id, @RequestParam String username){
        Optional<User> u = userRepository.findById(id);
        if(u.isPresent()){
            if(u.get().getUsername().equals(username))
                return "The user's name need not to update!";
            else {
                List <User> list = userRepository.findUser(username);
                if(list.isEmpty()) {
                    u.get().setUsername(username);
                    userRepository.save(u.get());
                    return u.get().print();
                }
                else return "The username is exist";
            }
        }
      else return "The user is not exist";
    }

    @DeleteMapping(path = "/delete")
    @ResponseBody
    public String deleteUser(@RequestParam int id){
        Optional<User> u=userRepository.findById(id);
        if(u.isPresent()){
            userRepository.deleteById(id);
            return "Delete successful!";
        }
        return "The user is not exist!";
    }
}
