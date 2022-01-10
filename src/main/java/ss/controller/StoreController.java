package ss.controller;

import ss.bo.QueryStoreBo;
import ss.constant.Constant;
import ss.po.Store;
import ss.po.View;
import ss.service.StoreService;
import ss.service.ViewService;
import ss.service.impl.StoreServiceImpl;
import ss.service.impl.ViewServiceImpl;
import ss.utils.ListUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class StoreController {

    static StoreService storeService = new StoreServiceImpl();

    static ViewService viewService = new ViewServiceImpl();

    public static String marketViewInput(View view, List<String> inputList){
        if(inputList.size()>1 && Constant.INSERT_ARG.equals(inputList.get(1))){
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员进行操作";
            }
            String productId = null;
            int storeNum = -1;
            for(int i  = 2;i<inputList.size();i += 2){
                if("-p".equals(inputList.get(i))){
                    productId = inputList.get(i+1);
                }
                if("-n".equals(inputList.get(i))){
                    storeNum = Integer.parseInt(inputList.get(i+1));
                }
            }
            if(productId != null){
                if(storeService.queryStoreByQueryBo(new QueryStoreBo(view.getMarketId(), productId)).isEmpty()){
                    if(storeNum != -1 && storeService.insertStore(view.getMarketId(), productId, storeNum)){
                        return "超市库存添加成功";
                    }
                    return "超市库存添加失败";
                }
                Store store = storeService.queryStoreByQueryBo(new QueryStoreBo(view.getMarketId(), productId)).get(0);
                store.setStoreNum(store.getStoreNum() + storeNum);
                if(storeNum != -1 && storeService.updateStore(store)){
                    return "超市中已有该商品,库存更新成功";
                }
            }
        }
        if(inputList.size()>2 && "-d".equals(inputList.get(1))){
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员进行操作";
            }
            if(storeService.deleteStore(view.getMarketId(), inputList.get(2))){
                return "库存:"+inputList.get(2)+"已删除";
            }
            return "库存删除失败";
        }
        QueryStoreBo storeBo = new QueryStoreBo(view.getMarketId(), null);
        if(inputList.size()>1){
            int minStoreNum = 0, maxStoreNum = -1;
            for(int i  = 1;i<inputList.size();i += 2){
                if("-p".equals(inputList.get(i))){
                    storeBo.setProductId(inputList.get(i+1));
                }
                if("-min".equals(inputList.get(i))){
                    minStoreNum = Integer.parseInt(inputList.get(i+1));
                }
                if("-max".equals(inputList.get(i))){
                    maxStoreNum = Integer.parseInt(inputList.get(i+1));
                }
            }
            List<Store> storeList = storeService.queryStoreByQueryBo(storeBo);
            for(int index = storeList.size()-1;index >= 0;index--){
                int indexOrderNum = storeList.get(index).getStoreNum();
                if(indexOrderNum < minStoreNum){
                    storeList.remove(index);
                    continue;
                }
                if(maxStoreNum != -1 && indexOrderNum > maxStoreNum){
                    storeList.remove(index);
                }
            }
        }
        return ListUtils.storeListString(storeService.queryStoreByQueryBo(storeBo));
    }
}
