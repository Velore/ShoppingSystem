package ss.utils;

import ss.po.*;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class ListUtils {

    public static String userListString(List<User> list){
        StringBuilder builder = new StringBuilder();
        for(User u : list){
            builder.append(u).append("\n");
        }
        return builder.toString();
    }

    /**
     * 将list转换为易于显示的String
     * @param list list
     * @return string
     */
    public static String orderListString(List<Order> list){
        StringBuilder builder = new StringBuilder();
        for(Order o : list){
            builder.append(o).append("\n");
        }
        return builder.toString();
    }

    public static String marketListString(List<Market> list){
        StringBuilder builder = new StringBuilder();
        for(Market m : list){
            builder.append(m).append("\n");
        }
        return builder.toString();
    }

    public static String storeListString(List<Store> list){
        StringBuilder builder = new StringBuilder();
        for(Store s : list){
            builder.append(s).append("\n");
        }
        return builder.toString();
    }

    public static String productListString(List<Product> list){
        StringBuilder builder = new StringBuilder();
        for(Product p : list){
            builder.append(p).append("\n");
        }
        return builder.toString();
    }
}
