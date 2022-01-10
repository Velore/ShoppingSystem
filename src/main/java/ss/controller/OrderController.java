package ss.controller;

import ss.bo.QueryOrderBo;
import ss.bo.QueryStoreBo;
import ss.constant.Constant;
import ss.po.Order;
import ss.po.View;
import ss.service.OrderService;
import ss.service.ViewService;
import ss.service.impl.OrderServiceImpl;
import ss.service.impl.StoreServiceImpl;
import ss.service.impl.ViewServiceImpl;
import ss.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class OrderController {

    static OrderService orderService = new OrderServiceImpl();
    static ViewService viewService = new ViewServiceImpl();

    public static String userViewInput(View view, List<String> inputList){
        if(inputList.size()>1 && Constant.ORDER_CANCEL_ARG.equals(inputList.get(1))){
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
            if((inputList.size() - 1)%2!=0){
                return "输入格式错误或参数缺失";
            }
            for(int i  = 1;i<inputList.size();i += 2){
                switch (inputList.get(i)){
                    case "-m":
                        orderBo.setMarketId(inputList.get(i+1));
                        break;
                    case "-p":
                        orderBo.setProductId(inputList.get(i+1));
                        break;
                    default:
                        System.out.println("存在异常参数");
                }
            }
        }
        return queryOrder(view, inputList);
    }

    public static String marketViewInput(View view, List<String> inputList){
        //添加订单
        if(inputList.size()>3 && Constant.INSERT_ARG.equals(inputList.get(1))){
            if(inputList.size()%2!=0){
                return "输入格式错误或参数缺失";
            }
            String productId = null;
            int orderNum = -1;
            for(int i  = 2;i<inputList.size();i += 2){
                switch (inputList.get(i)){
                    case "-p":
                        productId = inputList.get(i+1);
                        break;
                    case "-n":
                        orderNum = Integer.parseInt(inputList.get(i+1));
                        break;
                    default:
                        System.out.println("存在异常参数");
                }
            }
            if(new StoreServiceImpl().queryStoreByQueryBo(
                    new QueryStoreBo(view.getMarketId(), productId)).isEmpty()){
                return "该超市不存在指定商品";
            }
            if(orderNum <= 0){
                return "订购数量必须大于0";
            }
            if(orderService.insertOrder(new Order(
                    view.getUserId(),
                    view.getMarketId(),
                    productId,
                    orderNum))){
                return "订单添加成功";
            }
            return "订单添加失败";
        }
        if(!viewService.checkUser(view)){
            return "权限不足，请联系该超市管理员或root用户进行操作";
        }
        if(inputList.size()==3 && Constant.ORDER_CHECK_ARG.equals(inputList.get(1))){
            Order order = orderService.queryOrderByOrderId(inputList.get(2));
            if(order == null){
                return "订单不存在,请检查订单号";
            }
            if(order.getOrderStatus()!=0){
                return "订单已处理,无需再次处理";
            }
            if(orderService.updateOrder(order)){
                return "订单处理完成";
            }
            return "订单处理失败";
        }
        //查询订单
        return queryOrder(view, inputList);
    }

    public static String queryOrder(View view, List<String> inputList){
        QueryOrderBo orderBo = new QueryOrderBo(null, view.getMarketId(), null);
        if(inputList.size()==1 && view.getMarketId() != null){
            return ListUtils.orderListString(orderService.queryOrderByQueryOrderBo(
                    orderBo));
        }
        if(inputList.size()>1 && (inputList.size() - 1)%2!=0){
            return "输入格式错误或参数缺失";
        }
        int minOrderNum = 0, maxOrderNum = -1, orderStatus = -1;
        for(int i  = 1;i<inputList.size();i += 2){
            switch (inputList.get(i)){
                case "-u":
                    orderBo.setUserId(inputList.get(i+1));
                    break;
                case "-p":
                    orderBo.setProductId(inputList.get(i+1));
                    break;
                case "-min":
                    minOrderNum = Integer.parseInt(inputList.get(i+1));
                    break;
                case "-max":
                    maxOrderNum = Integer.parseInt(inputList.get(i+1));
                    break;
                case "-s":
                    orderStatus = Integer.parseInt(inputList.get(i+1));
                    break;
                default:
                    System.out.println("存在异常参数"+ inputList.get(i));
            }
        }
        List<Order> orderList = orderService.queryOrderByQueryOrderBo(orderBo);
        List<Order> returnList = new ArrayList<>();
        for(int index = orderList.size()-1;index >= 0;index--){
            int indexOrderNum = orderList.get(index).getOrderNum();
            if(maxOrderNum == -1 && indexOrderNum > minOrderNum){
                returnList.add(orderList.get(index));
                continue;
            }
            if(maxOrderNum != -1 && indexOrderNum < maxOrderNum && indexOrderNum > minOrderNum){
                returnList.add(orderList.get(index));
            }
        }
        if(orderStatus != -1){
            for(int index = returnList.size()-1;index >= 0;index--){
                if(returnList.get(index).getOrderStatus()!=orderStatus){
                    returnList.remove(index);
                }
            }
        }
        return ListUtils.orderListString(returnList);
    }
}
