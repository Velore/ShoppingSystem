package ss.service;

import ss.po.View;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public interface ViewService {

    /**
     * 检查用户是否为超市管理员
     * @param view view
     * @return true or false
     */
    boolean checkUser(View view);
}
