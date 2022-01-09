package ss.controller;

import ss.bo.QueryOrderBo;
import ss.po.Order;
import ss.po.View;
import ss.service.OrderService;
import ss.service.ViewService;
import ss.service.impl.OrderServiceImpl;
import ss.service.impl.ViewServiceImpl;
import ss.utils.ListUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class OrderController {

    static OrderService orderService = new OrderServiceImpl();

    static ViewService viewService = new ViewServiceImpl();

    public static String userViewInput(View view, List<String> inputList){
        if(inputList.size()>1 && "cancel".equals(inputList.get(1))){
            Order order = orderService.queryOrderByOrderId(inputList.get(2));
            order.setOrderStatus(3);
            if(orderService.updateOrder(order)){
                return "取消订单成功";
            }
            return "取消订单失败";
        }
        QueryOrderBo orderBo = new QueryOrderBo();
        orderBo.setUserId(view.getUserId());
        if(inputList.size()>1){
            for(int i  = 1;i<inputList.size();i += 2){
                if("-m".equals(inputList.get(i))){
                    orderBo.setMarketId(inputList.get(i+1));
                }
                if("-p".equals(inputList.get(i))){
                    orderBo.setProductId(inputList.get(i+1));
                }
            }
        }
        return ListUtils.orderListString(orderService.queryOrderByQueryOrderBo(orderBo));
    }

    public static String marketViewInput(View view, List<String> inputList){
        if(!viewService.checkUser(view)){
            return "权限不足，请联系该超市管理员或root用户进行操作";
        }
        //添加订单
        if(inputList.size()>3 && "ins".equals(inputList.get(1))){
            String productId = null;
            int orderNum = 0;
            for(int i  = 2;i<inputList.size();i += 2){
                if("-p".equals(inputList.get(i))){
                    productId = inputList.get(i+1);
                }
                if("-n".equals(inputList.get(i))){
                    orderNum = Integer.parseInt(inputList.get(i+1));
                }
            }
            if(orderService.insertOrder(new Order(view.getUserId(), view.getMarketId(), productId, orderNum))){
                return "订单添加成功";
            }
            return "订单添加失败";
        }
        if(inputList.size()>2 && "check".equals(inputList.get(1))){
            Order order = orderService.queryOrderByOrderId(inputList.get(2));
            order.setOrderStatus(1);
            if(orderService.updateOrder(order)){
                return "订单已完成";
            }
            return "订单失败";
        }
        //查询订单
        return queryOrder(view, inputList);
    }

    public static String queryOrder(View view, List<String> inputList){
        QueryOrderBo orderBo = new QueryOrderBo(view.getUserId(), view.getMarketId(), null);
        int minOrderNum = 0, maxOrderNum = -1, orderStatus = -1;
        for(int i  = 1;i<inputList.size();i += 2){
            if("-p".equals(inputList.get(i))){
                orderBo.setProductId(inputList.get(i+1));
            }
            if("-min".equals(inputList.get(i))){
                minOrderNum = Integer.parseInt(inputList.get(i+1));
            }
            if("-max".equals(inputList.get(i))){
                maxOrderNum = Integer.parseInt(inputList.get(i+1));
            }
            if("-s".equals(inputList.get(i))){
                orderStatus = Integer.parseInt(inputList.get(i+1));
            }
        }
        List<Order> orderList = orderService.queryOrderByQueryOrderBo(orderBo);
        for(int index = orderList.size()-1;index >= 0;index--){
            int indexOrderNum = orderList.get(index).getOrderNum();
            if(indexOrderNum < minOrderNum){
                orderList.remove(index);
                continue;
            }
            if(maxOrderNum != -1 && indexOrderNum > maxOrderNum){
                orderList.remove(index);
            }
        }
        if(orderStatus != -1){
            for(int index = orderList.size()-1;index >= 0;index--){
                if(orderList.get(index).getOrderStatus()!=orderStatus){
                    orderList.remove(index);
                }
            }
        }
        return ListUtils.orderListString(orderList);
    }
}
