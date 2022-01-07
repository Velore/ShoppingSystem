package ss.service.impl;

import org.apache.ibatis.session.SqlSession;
import ss.constant.Constant;
import ss.dao.UserMapper;
import ss.po.User;
import ss.service.UserService;
import ss.utils.MybatisUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/2
 **/
public class UserServiceImpl implements UserService {

    SqlSession session = MybatisUtils.getSqlSession();

    UserMapper userMapper = session.getMapper(UserMapper.class);

    @Override
    public boolean insertUser(String userId, String password) {
        if(userMapper.queryUserByUserId(userId)==null){
            if(userMapper.insertUser(new User(userId, Constant.DEFAULT_USERNAME, password)) == 1) {
                session.commit();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        if(userMapper.queryUserByUserId(user.getUserId())==null){
            return false;
        }
        if(userMapper.updateUser(user)==1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }

    @Override
    public User queryUserByUserId(String userId) {
        return userMapper.queryUserByUserId(userId);
    }

    @Override
    public List<User> queryUserLikeName(String username) {
        return userMapper.queryUserLikeName(username);
    }
}
