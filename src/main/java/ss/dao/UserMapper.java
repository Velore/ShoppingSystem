package ss.dao;

import ss.po.User;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/2
 **/
public interface UserMapper {

    /**
     * 插入新的用户
     * @param user user
     * @return 是否插入成功
     */
    int insertUser(User user);

    /**
     * 更新用户
     * @param user user
     * @return 是否更新成功
     */
    int updateUser(User user);

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
