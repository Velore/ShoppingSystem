package ss.service.impl;

import org.springframework.stereotype.Service;
import ss.dao.UserMapper;

import javax.annotation.Resource;

/**
 * @author Velore
 * @date 2022/1/2
 **/
@Service
public class UserServiceImpl {

    @Resource
    public UserMapper userMapper;
}
