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
import ss.utils.InputUtils;
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
        //取消订单
        if(inputList.size()>1 && Constant.ORDER_CANCEL_ARG.equals(inputList.get(1))){
            Order order = orderService.queryOrderByOrderId(inputList.get(2));
            //设置订单状态为用户取消(状态'3')
            order.setOrderStatus(3);
            if(orderService.updateOrder(order)){
                return "取消订单成功";
            }
            return "取消订单失败";
        }
        //查询订单
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
                    //要订购的商品id
                    case "-p": productId = inputList.get(i+1);
                        break;
                    case "-n":
                        //订购数量
                        if(!InputUtils.isNumString(inputList.get(i+1))){
                            return "必需的参数不能转换为数字";
                        }
                        orderNum = Integer.parseInt(inputList.get(i+1));
                        break;
                    default:
                        System.out.println("存在异常参数");
                }
            }
            //检查该超市内是否存在指定商品的库存
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
        //检查用户是否为超市管理员
        if(!viewService.checkUser(view)){
            return "权限不足，请联系该超市管理员或root用户进行操作";
        }
        //检查并处理订单
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
        //删除订单
        if(inputList.size() >=3 && Constant.DELETE_ARG.equals(inputList.get(1))){
            Order order = orderService.queryOrderByOrderId(inputList.get(inputList.size()-1));
            if(order==null){
                return "订单不存在";
            }
            if(order.getOrderStatus()==0){
                return "订单未被处理,不允许删除";
            }
            if(orderService.deleteOrder(order.getOrderId())){
                return "订单删除成功";
            }
            return "订单删除失败";
        }
        //查询订单
        return queryOrder(view, inputList);
    }

    /**
     * 查询订单
     * @param view 视图信息
     * @param inputList input list
     * @return String
     */
    public static String queryOrder(View view, List<String> inputList){
        QueryOrderBo orderBo = new QueryOrderBo(view.getUserId(), view.getMarketId(), null);
        if(inputList.size()==1 && view.getMarketId() != null){
            orderBo.setUserId(null);
            return ListUtils.orderListString(orderService.queryOrderByQueryOrderBo(orderBo));
        }
        if(inputList.size()>1 && (inputList.size() - 1)%2!=0){
            return "输入格式错误或参数缺失";
        }
        int minOrderNum = 0, maxOrderNum = -1, orderStatus = -1;
        String queryMarketId = null, queryUserId = null;
        for(int i  = 1;i<inputList.size();i += 2){
            switch (inputList.get(i)){
                case "-m":
                    //用户界面查看订单时该参数有效,限定超市
                    queryMarketId = inputList.get(i+1);
                    break;
                case "-u":
                    //超市界面查看订单时该参数有效,限定订购用户
                    queryUserId = inputList.get(i+1);
                    break;
                case "-p":
                    //限定订单的商品
                    orderBo.setProductId(inputList.get(i+1));
                    break;
                case "-min":
                    //限定最小订购数量
                    if(InputUtils.isNumString(inputList.get(i+1))){
                        minOrderNum = Integer.parseInt(inputList.get(i+1));
                    }else {
                        System.out.println("[-min]参数不能转换为数字");
                    }
                    break;
                case "-max":
                    //限定最大订购数量
                    if(InputUtils.isNumString(inputList.get(i+1))){
                        maxOrderNum = Integer.parseInt(inputList.get(i+1));
                    }else {
                        System.out.println("[-max]参数不能转换为数字");
                    }
                    break;
                case "-s":
                    //限定订单状态
                    if(InputUtils.isNumString(inputList.get(i+1))){
                        orderStatus = Integer.parseInt(inputList.get(i+1));
                    }else {
                        System.out.println("[-s]参数不能转换为数字");
                    }
                    break;
                default:
                    System.out.println("存在异常参数"+ inputList.get(i));
            }
        }
        if(view.getMarketId()==null){
            orderBo.setUserId(view.getUserId());
            orderBo.setMarketId(queryMarketId);
        }else{
            orderBo.setUserId(queryUserId);
            orderBo.setMarketId(view.getMarketId());
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
