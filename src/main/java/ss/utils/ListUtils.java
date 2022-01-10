package ss.utils;

import ss.po.*;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class ListUtils {

    /**
     * 将list转换为易于显示的String
     * @param list list
     * @return string
     */
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
        if(list.isEmpty()){
            return "空列表";
        }
        StringBuilder builder = new StringBuilder();
        for(Order o : list){
            builder.append(o).append("\n");
        }
        return builder.toString();
    }

    /**
     * 将list转换为易于显示的String
     * @param list list
     * @return string
     */
    public static String marketListString(List<Market> list){
        if(list.isEmpty()){
            return "空列表";
        }
        StringBuilder builder = new StringBuilder();
        for(Market m : list){
            builder.append(m).append("\n");
        }
        return builder.toString();
    }

    /**
     * 将list转换为易于显示的String
     * @param list list
     * @return string
     */
    public static String storeListString(List<Store> list){
        if(list.isEmpty()){
            return "空列表";
        }
        StringBuilder builder = new StringBuilder();
        for(Store s : list){
            builder.append(s).append("\n");
        }
        return builder.toString();
    }

    /**
     * 将list转换为易于显示的String
     * @param list list
     * @return string
     */
    public static String productListString(List<Product> list){
        if(list.isEmpty()){
            return "空列表";
        }
        StringBuilder builder = new StringBuilder();
        for(Product p : list){
            builder.append(p).append("\n");
        }
        return builder.toString();
    }
}
