package com.thoughtworks.homework.service;
import com.thoughtworks.homework.dto.UserDTO;
import com.thoughtworks.homework.entity.User;
import com.thoughtworks.homework.exception.BaseUserException;
import com.thoughtworks.homework.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private static User ZHANG_SAN = new User("yq",18,"male");


    @Test
    public void should_throw_exception_when_create_user_with_username_is_exist(){
        when(userRepository.findUser(anyString())).thenReturn(java.util.Optional.ofNullable(ZHANG_SAN));

        BaseUserException exception = assertThrows(BaseUserException.class,() ->userService.creatUser(ZHANG_SAN));

        assertEquals("用户已存在",exception.getMessage());
    }

    @Test
    public void should_return_userInfo_when_create_user_with_username_is_not_used() throws BaseUserException {
        when(userRepository.findUser(anyString())).thenReturn(Optional.empty());

        UserDTO<User> u = userService.creatUser(ZHANG_SAN);

        assertEquals("添加用户成功！",u.getMessage());
    }

    @Test
    public void should_return_usersInfo_when_get_users(){

        when(userRepository.findAll()).thenReturn((Iterable<User>) ZHANG_SAN);

        UserDTO<Iterable<User>> u = userService.getAllUsers();

        assertEquals("数据获取成功！",u.getMessage());
    }

}
