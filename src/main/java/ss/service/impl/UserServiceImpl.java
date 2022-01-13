package ss.service.impl;

import org.apache.ibatis.session.SqlSessionManager;
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

    @Override
    public boolean loginIn(String userId, String password) {
        UserMapper mapper = MybatisUtils.getMapper(UserMapper.class);
        User user = mapper.queryUserByUserId(userId);
        return user != null && password.equals(user.getPassword());
    }

    @Override
    public boolean insertUser(String userId, String password) {
        UserMapper mapper = MybatisUtils.getMapper(UserMapper.class);
        if(mapper.queryUserByUserId(userId)==null){
            return mapper.insertUser(new User(userId, Constant.DEFAULT_USERNAME, password)) == 1;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        SqlSessionManager manager = MybatisUtils.getSessionManager();
        manager.startManagedSession();
        UserMapper mapper = MybatisUtils.getMapper(UserMapper.class);
        if(mapper.queryUserByUserId(user.getUserId())==null){
            manager.commit();
            return false;
        }
        if(mapper.updateUser(user)==1){
            manager.commit();
            return true;
        }
        manager.commit();
        return false;
    }

    @Override
    public List<User> queryAllUser() {
        UserMapper mapper = MybatisUtils.getMapper(UserMapper.class);
        return mapper.queryAllUser();
    }

    @Override
    public User queryUserByUserId(String userId) {
        UserMapper mapper = MybatisUtils.getMapper(UserMapper.class);
        return mapper.queryUserByUserId(userId);
    }

    @Override
    public List<User> queryUserLikeName(String username) {
        UserMapper mapper = MybatisUtils.getMapper(UserMapper.class);
        return mapper.queryUserLikeName(username);
    }
}
