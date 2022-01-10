package ss.controller;

import ss.bo.QueryMarketBo;
import ss.constant.Constant;
import ss.po.Market;
import ss.po.View;
import ss.service.MarketService;
import ss.service.OrderService;
import ss.service.StoreService;
import ss.service.ViewService;
import ss.service.impl.MarketServiceImpl;
import ss.service.impl.OrderServiceImpl;
import ss.service.impl.StoreServiceImpl;
import ss.service.impl.ViewServiceImpl;
import ss.utils.ListUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class MarketController {

    static MarketService marketService = new MarketServiceImpl();
    static OrderService orderService = new OrderServiceImpl();
    static StoreService storeService = new StoreServiceImpl();
    static ViewService viewService = new ViewServiceImpl();

    public static String queryMarket(View view, List<String> inputList){
        // 查询全部超市
        // 该参数存在时,其余查询限定条件无效
        if(inputList.contains("-all")){
            return ListUtils.marketListString(marketService.queryAllMarket());
        }
        if(inputList.size()>1 && (inputList.size()+1)%2!=0){
            return "输入格式错误或参数缺失";
        }
        QueryMarketBo marketBo = new QueryMarketBo();
        //如果是root用户,默认查询全部超市,普通用户默认查询自己管理的超市
        marketBo.setUserId(Constant.DEFAULT_ADMIN_ID.equals(view.getUserId()) ? null: view.getUserId());
        String queryUserId = null;
        for(int i  = 1;i<inputList.size();i += 2){
            //限定超市管理员
            if("-u".equals(inputList.get(i))){
                queryUserId = inputList.get(i+1);
            }
            //限定超市名
            if("-n".equals(inputList.get(i))){
                marketBo.setMarketName(inputList.get(i+1));
            }
        }
        if(queryUserId != null && !view.getUserId().equals(queryUserId)){
            marketBo.setUserId(queryUserId);
        }else {
            marketBo.setUserId(view.getUserId());
        }
        return ListUtils.marketListString(marketService.queryMarketByQueryMarketBo(marketBo));
    }

    public static String userViewInput(View view, List<String> inputList){
        //删除超市
        if(inputList.size()>2 &&"-d".equals(inputList.get(1))){
            //若存在多个参数,则只有最后一个参数作为marketId
            view.setMarketId(inputList.get(inputList.size()-1));
            //检查用户是否为超市管理员
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员或root用户进行操作";
            }
            //检查是否存在未处理订单
            if(!orderService.isOrderAllCheck(view.getMarketId())){
                return view.getMarketId()+"存在未完成订单,不可删除超市";
            }
            //删除超市库存
            if(!storeService.deleteAllStoreByMarketId(view.getMarketId())){
                return view.getMarketId()+"库存删除失败";
            }
            //删除超市
            if(marketService.deleteMarket(view.getMarketId())){
                return "超市"+view.getMarketId()+"删除成功";
            }
            return "超市"+view.getMarketId()+"删除失败";
        }
        //创建超市
        if(inputList.size()>2 && Constant.INSERT_ARG.equals(inputList.get(1))){
            if (marketService.insertMarket(inputList.get(2), view.getUserId())) {
                return "创建超市成功";
            }
            return "创建超市失败";
        }
        //查询超市信息
        return queryMarket(view, inputList);
    }

    public static String marketViewInput(View view, List<String> inputList){
        Market market = marketService.queryMarketByMarketId(view.getMarketId());
        //修改超市信息
        if(inputList.size()>1 && Constant.ALTER_ARG.equals(inputList.get(1))){
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员或root用户进行操作";
            }
            if(inputList.size()>1 && inputList.size()%2!=0){
                return "输入格式错误或参数缺失";
            }
            for(int i = 2;i<inputList.size();i += 2){
                if("-n".equals(inputList.get(i))){
                    market.setMarketName(inputList.get(i+1));
                }
                if("-u".equals(inputList.get(i))){
                    market.setUserId(inputList.get(i+1));
                }
            }
            if(marketService.updateMarket(market)){
                return "修改成功";
            }
            return "修改失败";
        }
        //在超市界面时,默认查询查询当前超市信息
        if(inputList.size() == 1){
            return marketService.queryMarketByMarketId(view.getMarketId()).toString();
        }
        return queryMarket(view, inputList);
    }
}
