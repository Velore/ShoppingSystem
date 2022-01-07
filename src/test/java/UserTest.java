import org.junit.jupiter.api.Test;
import ss.po.User;
import ss.service.UserService;
import ss.service.impl.UserServiceImpl;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/2
 **/
public class UserTest {

    final UserService userService = new UserServiceImpl();

    @Test
    public void insertUserTest(){
        System.out.println(userService.insertUser("test","pwd"));
        System.out.println(userService.queryUserByUserId("test"));
    }

    @Test
    public void updateUserTest(){
        User user = userService.queryUserByUserId("u1");
        user.setUsername("testName");
        System.out.println(userService.updateUser(user));
        System.out.println(userService.queryUserByUserId("u1"));
    }

    @Test
    public void queryAllUserTest(){
        List<User> userList = userService.queryAllUser();
        for(User u : userList){
            System.out.println(u.toString());
        }
    }

    @Test
    public void queryUserByUserIdTest(){
        System.out.println(userService.queryUserByUserId("u1"));
    }

    @Test
    public void queryUserLikeNameTest(){
        System.out.println(userService.queryUserLikeName("2"));
    }
}
