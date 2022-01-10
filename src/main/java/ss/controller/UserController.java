package ss.controller;

import ss.constant.Constant;
import ss.po.User;
import ss.po.View;
import ss.service.UserService;
import ss.service.impl.UserServiceImpl;
import ss.utils.ListUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class UserController {

    static UserService userService = new UserServiceImpl();

    public static String mainViewInput(View view, List<String> inputList){
        if((inputList.size()-1)% 2 !=0){
            return "输入格式错误,请输入完整的登录信息";
        }
        String loginUserId = null, loginPassword = null;
        for(int i = 1;i<inputList.size();i += 2){
            if("-i".equals(inputList.get(i))){
                loginUserId = inputList.get(i+1);
            }
            if("-p".equals(inputList.get(i))){
                loginPassword = inputList.get(i+1);
            }
        }
        if(loginUserId == null || loginPassword == null){
            return "帐号或密码为空,请输入完整的登录信息";
        }else {
            if(userService.loginIn(loginUserId, loginPassword)){
                view.setUserId(loginUserId);
                return "用户登录成功";
            }
            return "用户id或密码错误";
        }
    }

    public static String userViewInput(View view, List<String> inputList){
        User user = userService.queryUserByUserId(view.getUserId());
        if(inputList.size()>1 && Constant.ALTER_ARG.equals(inputList.get(1))){
            if(inputList.size() % 2 != 0 ) { return "输入格式错误或参数缺失"; }
            for(int i = 2;i<inputList.size();i += 2){
                if("-n".equals(inputList.get(i))){
                    user.setUsername(inputList.get(i+1));
                }
                if("-p".equals(inputList.get(i))){
                    user.setPassword(inputList.get(i+1));
                }
                //管理员修改用户信息
                if(Constant.DEFAULT_ADMIN_ID.equals(view.getUserId())&&"-i".equals(inputList.get(i))){
                    user.setUsername(inputList.get(i+1));
                }
            }
            if(userService.updateUser(user)){
                return "用户信息修改成功";}
            return "用户信息修改失败";
        }
        if(!Constant.DEFAULT_ADMIN_ID.equals(view.getUserId())){
            return userService.queryUserByUserId(view.getUserId()).toString();
        }
        return ListUtils.userListString(userService.queryAllUser());
    }
}
