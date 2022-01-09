package ss.service;

import ss.po.User;
import java.util.List;

/**
 * @author Velore
 * @date 2022/1/2
 **/
public interface UserService {

    /**
     * 登录
     * @param userId id
     * @param password pwd
     * @return 是否登录成功
     */
    boolean loginIn(String userId, String password);

    /**
     * 插入新的用户
     * @param userId userId
     * @param password password
     * @return 是否插入成功
     */
    boolean insertUser(String userId, String password);

    /**
     * 修改用户信息
     * @param user user
     * @return 是否修改成功
     */
    boolean updateUser(User user);

    /**
     * 查询全部用户
     * @return user list
     */
    List<User> queryAllUser();

    /**
     * 根据用户id查询用户
     * @param userId userId
     * @return user
     */
    User queryUserByUserId(String userId);

    /**
     * 根据用户名模糊查询用户
     * @param username username
     * @return user list
     */
    List<User> queryUserLikeName(String username);
}
