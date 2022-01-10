package ss.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

/**
 * mybatis工具类
 * @author Velore
 * @date 2022/1/2
 **/
public class MybatisUtils {

    private static SqlSessionFactory sqlSessionFactory = null;
    static{
        try{
            String resource = "db/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取SqlSession
     * @return SqlSession
     */
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
//
//    public class MybatisUtils {
//
//        private static SqlSessionManager sessionManager = null;
//
//        static {
//            try {
//                InputStream inputStream = MybatisUtils.class.getClassLoader()
//                        .getResourceAsStream("mybatis-config1.xml");
//                sessionManager = SqlSessionManager.newInstance(inputStream);
//            } catch (Exception e) {
//                throw new RuntimeException("the Configuration of Mybatis is not exist!");
//            }
//        }
//
//        /**
//         * 获取 Mapper
//         * @param <T>
//         * @param clazz
//         * @return
//         */
//        public static <T> T getMapper(Class<T> clazz) {
//            return sessionManager.getMapper(clazz);
//        }
//
//        /**
//         * 获取 SqlSessionManager
//         * @return
//         */
//        public static SqlSessionManager getSessionManager() {
//            return sessionManager;
//        }
//    }

}
