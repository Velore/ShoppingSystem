package ss.controller;

import ss.bo.QueryMarketBo;
import ss.bo.QueryOrderBo;
import ss.bo.QueryStoreBo;
import ss.constant.Constant;
import ss.constant.InputFormat;
import ss.po.View;
import ss.service.*;
import ss.service.impl.*;
import ss.utils.InputUtils;
import ss.utils.ListUtils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class ViewController {

    static Scanner scanner = new Scanner(System.in);

    static String input;

    static List<String> inputList;

    static UserService userService = new UserServiceImpl();

    static MarketService marketService = new MarketServiceImpl();

    static OrderService orderService = new OrderServiceImpl();

    static StoreService storeService = new StoreServiceImpl();

    public static void mainView(View view){
        input = null;
        inputList = null;
        System.out.println("--------系统主界面---------");
        System.out.println("输入help查询当前页面全部命令");
        while (true){
            view.setUserId(null);
            view.setMarketId(null);
            try{
                do{
                    System.out.print("main->");
                    input = scanner.nextLine();
                }while (input == null);
                inputList = InputUtils.defaultInputSplit(input);
                switch (inputList.get(0)){
                    case "reg":
                        if(userService.insertUser(inputList.get(1), inputList.get(2))){
                            System.out.println("用户注册成功");
                            break;
                        }
                        break;
                    case "login":
                        System.out.println(UserController.mainViewInput(view, inputList));
                        if(view.getUserId()!=null){
                            userView(view);
                        }
                        break;
                    case "help":
                        System.out.println("--------注：[..]内参数无特殊说明均为选填---------");
                        for(Map.Entry<String, String> entry:InputFormat.MAIN_VIEW.entrySet()){
                            System.out.println(entry.getKey()+": '"+entry.getValue()+"'");
                        }
                        break;
                    case "exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("请输入正确的命令,输入help查询当前页面全部命令");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void userView(View view){
        input = null;
        inputList = null;
        System.out.println("----------用户界面---------");
        System.out.println("输入help查询当前页面全部命令");
        System.out.println("当前用户:"+userService.queryUserByUserId(view.getUserId()).getUsername());
        if(Constant.DEFAULT_ADMIN_ID.equals(view.getUserId())){
            System.out.println("->"+view.getUserId()+"[market]:\n"+ListUtils.marketListString(marketService.queryAllMarket()));
        }else{
            System.out.println("->"+view.getUserId()+"[market]:\n"+ListUtils.marketListString(marketService.queryMarketByQueryMarketBo(
                    new QueryMarketBo(null, view.getUserId()))));
        }
        if(Constant.DEFAULT_ADMIN_ID.equals(view.getUserId())){
            System.out.println("->"+view.getUserId()+"[order]:\n"+ListUtils.orderListString(orderService.queryAllOrder()));
        }else{
            System.out.println("->"+view.getUserId()+"[order]:\n"+ListUtils.orderListString(orderService.queryOrderByQueryOrderBo(
                    new QueryOrderBo(view.getUserId(), null, null))));
        }
        while (true){
            view.setMarketId(null);
            try{
                do{
                    System.out.print(view.getUserId()+"->");
                    input = scanner.nextLine();
                }while (input == null);
                inputList = InputUtils.defaultInputSplit(input);
                if("exit".equals(inputList.get(0))){
                    break;
                }
                switch (inputList.get(0)){
                    case "help":
                        System.out.println("--------注：[..]内参数无特殊说明均为选填---------");
                        for(Map.Entry<String, String> entry:InputFormat.USER_VIEW.entrySet()){
                            System.out.println(entry.getKey()+": '"+entry.getValue()+"'");
                        }
                        break;
                    case "user":
                        System.out.println(UserController.userViewInput(view, inputList));
                        break;
                    case "order":
                        System.out.println(OrderController.userViewInput(view, inputList));
                        break;
                    case "market":
                        System.out.println(MarketController.userViewInput(view, inputList));
                        break;
                    case "prod":
                        System.out.println(ProductController.viewInput(view, inputList));
                        break;
                    case "into":
                        if(inputList.size()>1 && marketService.queryMarketByMarketId(inputList.get(1))!=null){
                            view.setMarketId(inputList.get(1));
                            marketView(view);
                        }else if(inputList.size() <= 1){
                            System.out.println("命令不完整,格式为' into marketId '");
                        }else {
                            System.out.println("超市不存在");
                        }
                        break;
                    default:
                        System.out.println("请输入正确的命令,输入help查询当前页面全部命令");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void marketView(View view) {
        input = null;
        inputList = null;
        System.out.println("----------超市界面---------");
        System.out.println("输入help查询当前页面全部命令");
        System.out.println("当前超市:" + marketService.queryMarketByMarketId(view.getMarketId()).getMarketName());
        System.out.println("->"+view.getMarketId() + "[store]:" + ListUtils.storeListString(storeService.queryStoreByQueryBo(
                new QueryStoreBo(view.getMarketId(), null))));
        System.out.println("->"+view.getMarketId() + "[order]:" + ListUtils.orderListString(orderService.queryOrderByQueryOrderBo(
                new QueryOrderBo(null, view.getMarketId(), null))));
        while (true) {
            try{
                do {
                    System.out.print(view.getUserId()+"-"+view.getMarketId()+"->");
                    input = scanner.nextLine();
                } while (input == null);
                inputList = InputUtils.defaultInputSplit(input);
                if ("exit".equals(inputList.get(0))) {
                    break;
                }
                switch (inputList.get(0)){
                    case "help":
                        System.out.println("--------注：[..]内参数无特殊说明均为选填---------");
                        for(Map.Entry<String, String> entry:InputFormat.MARKET_VIEW.entrySet()){
                            System.out.println(entry.getKey()+": '"+entry.getValue()+"'");
                        }
                        break;
                    case "prod":
                        System.out.println(ProductController.viewInput(view, inputList));
                        break;
                    case "market":
                        System.out.println(MarketController.marketViewInput(view, inputList));
                        break;
                    case "order":
                        System.out.println(OrderController.marketViewInput(view, inputList));
                        break;
                    case "store":
                        System.out.println(StoreController.marketViewInput(view, inputList));
                        break;
                    default:
                        System.out.println("请输入正确的命令,输入help查询当前页面全部命令");
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
