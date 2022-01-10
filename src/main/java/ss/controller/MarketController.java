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
        QueryMarketBo marketBo = new QueryMarketBo();
        marketBo.setUserId(view.getUserId());
        boolean isAll = false;
        for(int i  = 1;i<inputList.size();i += 2){
            if("-u".equals(inputList.get(i))){
                marketBo.setUserId(inputList.get(i+1));
            }
            if("-n".equals(inputList.get(i))){
                marketBo.setMarketName(inputList.get(i+1));
            }
            if("-all".equals(inputList.get(i))){
                isAll = true;
            }
        }
        if(isAll){
            return ListUtils.marketListString(marketService.queryAllMarket());
        }else {
            if(marketBo.getMarketName()!=null || Constant.DEFAULT_ADMIN_ID.equals(view.getUserId())){
                marketBo.setUserId(null);
            }
            return ListUtils.marketListString(marketService.queryMarketByQueryMarketBo(marketBo));
        }
    }

    public static String userViewInput(View view, List<String> inputList){
        if(inputList.size()>2 &&"-d".equals(inputList.get(1))){
            view.setMarketId(inputList.get(2));
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员或root用户进行操作";
            }
            //检查是否存在未处理订单
            if(!orderService.checkOrder(view.getMarketId())){
                return view.getMarketId()+"存在未完成订单,不可删除超市";
            }
            //删除超市库存
            if(!storeService.deleteAllStoreByMarketId(view.getMarketId())){
                return view.getMarketId()+"库存删除失败";
            }
            if(marketService.deleteMarket(view.getMarketId())){
                return view.getMarketId()+"删除成功";
            }
            return view.getMarketId()+"删除失败";
        }
        if(inputList.size()>2 && Constant.INSERT_ARG.equals(inputList.get(1))){
            if (marketService.insertMarket(inputList.get(2), view.getUserId())) {
                return "创建超市成功";
            }
            return "创建超市失败";
        }

        return queryMarket(view, inputList);
    }

    public static String marketViewInput(View view, List<String> inputList){
        Market market = marketService.queryMarketByMarketId(view.getMarketId());
        //修改超市信息
        if(inputList.size()>1 && Constant.ALTER_ARG.equals(inputList.get(1))){
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员或root用户进行操作";
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
        if(inputList.size() == 1){
            return marketService.queryMarketByMarketId(view.getMarketId()).toString();
        }
        return queryMarket(view, inputList);
    }
}
