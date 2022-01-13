package ss.utils;

import org.apache.ibatis.session.SqlSessionManager;

import java.io.InputStream;

/**
 * mybatis工具类
 * @author Velore
 * @date 2022/1/2
 **/
public class MybatisUtils {

    private static final SqlSessionManager SESSION_MANAGER;

    static {
        try {
            InputStream inputStream = MybatisUtils.class.getClassLoader()
                    .getResourceAsStream("db/mybatis-config.xml");
            SESSION_MANAGER = SqlSessionManager.newInstance(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("获取mybatis配置文件错误");
        }
    }

    /**
     * 获取 Mapper
     * @param <T> mapper类型
     * @param clazz class
     * @return mapper
     */
    public static <T> T getMapper(Class<T> clazz) {
        return SESSION_MANAGER.getMapper(clazz);
    }

    /**
     * 获取 SqlSessionManager
     * @return SqlSessionManager
     */
    public static SqlSessionManager getSessionManager() {
        return SESSION_MANAGER;
    }
}
