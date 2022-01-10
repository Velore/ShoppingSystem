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

    public static void printGuide() {
        System.out.println("\n命令由'前缀'和'参数'组成,如' login [-i userId](必填) [-p password](必填) '" +
                "\n\t没有带'[]'括号的是命令前缀,必须填写" +
                "\n\t'[]'括号内为参数,默认可填可不填,若标识(必填)则必须填写");
        System.out.println("参数一般由'类型'和'值'组成,如[-i userId]中'-i'是参数类型,'userId'为参数的值");
        System.out.println("特殊情况下命令的参数只需输入值或者只需输入类型,如' market [-all] '或者' prod [productName]'\n" +
                "查询全部超市的命令只需输入命令前缀和参数类型：'market [-all]',此时' market '为命令前缀,'-all '为参数类型\n" +
                "大部分只有一个参数的命令只需输入命令前缀和参数的值," +
                "如用户取消订单：'order cancel [orderId](必填)',此时' order cancel '为命令前缀,'orderId'为参数的值");
        System.out.println("少部分命令需要输入多个参数而没有参数类型,如注册命令' reg [userId](必填) [password](必填) '," +
                "则只能第一个参数填写userId,第二个参数填password");
        System.out.println("具体命令通过help查看\n");
    }

    public static void mainView(View view){
        input = null;
        inputList = null;
        System.out.println("--------系统主界面---------");
        System.out.println("欢迎使用购物管理系统ShoppingSystem");
        System.out.println("输入help查询当前页面全部命令,输入guide查看对输入命令的介绍");
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
                        if(inputList.size() != 3){
                            System.out.println("参数异常");
                            break;
                        }
                        if(userService.insertUser(inputList.get(1), inputList.get(2))){
                            System.out.println("用户注册成功");
                            break;
                        }
                        System.out.println("用户注册失败,可能原因为id重复");
                        break;
                    case "login":
                        System.out.println(UserController.mainViewInput(view, inputList));
                        if(view.getUserId()!=null){
                            userView(view);
                        }
                        break;
                    case "help":
                        for(Map.Entry<String, String> entry:InputFormat.MAIN_VIEW.entrySet()){
                            System.out.println(entry.getKey()+": '"+entry.getValue()+"'");
                        }
                        System.out.println("--------注：[..]内参数无特殊说明均为可填可不填---------\n");
                        break;
                    case "guide": printGuide();
                        break;
                    case "exit": System.exit(0);
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
            System.out.println("->"+view.getUserId()+"[market]:\n"+
                    ListUtils.marketListString(marketService.queryAllMarket()));
        }else{
            System.out.println("->"+view.getUserId()+"[market]:\n"+
                    ListUtils.marketListString(marketService.queryMarketByQueryMarketBo(
                    new QueryMarketBo(null, view.getUserId()))));
        }
        if(!Constant.DEFAULT_ADMIN_ID.equals(view.getUserId())){
            System.out.println("->"+view.getUserId()+"[order]:\n"+
                    ListUtils.orderListString(orderService.queryOrderByQueryOrderBo(
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
                    case "guide": printGuide();
                        break;
                    case "shutdown": System.exit(0);
                        break;
                    case "help":
                        for(Map.Entry<String, String> entry:InputFormat.USER_VIEW.entrySet()){
                            System.out.println(entry.getKey()+": '"+entry.getValue()+"'");
                        }
                        System.out.println("--------注：[..]内参数无特殊说明均为可填可不填---------\n");
                        break;
                    case "user": System.out.println(UserController.userViewInput(view, inputList));
                        break;
                    case "store": System.out.println(StoreController.queryStore(view, inputList));
                        break;
                    case "order": System.out.println(OrderController.userViewInput(view, inputList));
                        break;
                    case "market": System.out.println(MarketController.userViewInput(view, inputList));
                        break;
                    case "prod": System.out.println(ProductController.viewInput(view, inputList));
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
                    default: System.out.println("请输入正确的命令,输入help查询当前页面全部命令");
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
        System.out.println("->"+view.getMarketId() + "[store]:\n" +
                ListUtils.storeListString(storeService.queryStoreByQueryBo(
                new QueryStoreBo(view.getMarketId(), null))));
        System.out.println("->"+view.getMarketId() + "[order]:\n" +
                ListUtils.orderListString(orderService.queryOrderByQueryOrderBo(
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
                    case "guide": printGuide();
                        break;
                    case "shutdown": System.exit(0);
                        break;
                    case "help":
                        for(Map.Entry<String, String> entry:InputFormat.MARKET_VIEW.entrySet()){
                            System.out.println(entry.getKey()+": '"+entry.getValue()+"'");
                        }
                        System.out.println("--------注：[..]内参数无特殊说明均为可填可不填---------\n");
                        break;
                    case "prod": System.out.println(ProductController.viewInput(view, inputList));
                        break;
                    case "market": System.out.println(MarketController.marketViewInput(view, inputList));
                        break;
                    case "order": System.out.println(OrderController.marketViewInput(view, inputList));
                        break;
                    case "store": System.out.println(StoreController.marketViewInput(view, inputList));
                        break;
                    default: System.out.println("请输入正确的命令,输入help查询当前页面全部命令");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
