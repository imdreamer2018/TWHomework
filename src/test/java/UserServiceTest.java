import com.thoughtworks.homework.dto.UserDTO;
import com.thoughtworks.homework.entity.User;
import com.thoughtworks.homework.exception.BaseUserException;
import com.thoughtworks.homework.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Mock
    private User user;  // 模拟对象
    private UserDTO<User> userDTO;  // 被测类
    private UserService userService;

    public UserServiceTest() {
    }

    // 在@Test标注的测试方法之前运行
    @Before
    public void setUp() throws Exception {
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象
        MockitoAnnotations.initMocks(this);
        // 用模拟对象创建被测类对象
        userDTO = new UserService().creatUser(user);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void user_userService_addUser_test() throws BaseUserException {
        User user = new User();
        user.setUsername("tyx");
        user.setAge(24);
        user.setGender("male");
        //when(userService.creatUser(user)).thenReturn(null);
        UserDTO<User> userResult = new UserService().creatUser(user);

    }


    @Test(expected = Exception.class)
    public void testCreateExistUser() throws Exception {
        UserService userService = new UserService();

    }


    @Test
    public UserDTO<User> addUser() throws BaseUserException {

        return userService.creatUser(user);


    }

    @Test
    public UserDTO<Iterable<User>> findUsers() {
        return userService.getAllUsers();
    }

    @Test
    public UserDTO<User> findUserById() throws BaseUserException {
        int id = 1;
        return userService.findUserById(id);
    }

    @Test
    public UserDTO<User> updateById() throws BaseUserException {
        User user = new User();
        user.setId(1);
        user.setUsername("tyx");
        user.setAge(24);
        user.setGender("male");
        return userService.updateUserById(user);
    }

    @Test
    public UserDTO<User> deleteUser() throws BaseUserException {
        int id=1;
        return userService.deleteUser(id);
    }
}
