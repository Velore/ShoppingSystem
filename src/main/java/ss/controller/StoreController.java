package ss.controller;

import ss.bo.QueryStoreBo;
import ss.constant.Constant;
import ss.po.Store;
import ss.po.View;
import ss.service.StoreService;
import ss.service.ViewService;
import ss.service.impl.ProductServiceImpl;
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
public class StoreController {

    static StoreService storeService = new StoreServiceImpl();

    static ViewService viewService = new ViewServiceImpl();

    public static String queryStore(View view, List<String> inputList){
        if(view.getMarketId()==null && !inputList.contains("-p") && !inputList.contains("-m")){
            return "至少需要指定[-p productId]参数(商品名)和[-m marketId]参数(超市id)其中一个";
        }
        if(inputList.size() > 1 && (inputList.size() + 1) % 2 != 0){
            return "输入格式错误或参数缺失";
        }
        QueryStoreBo storeBo = new QueryStoreBo(view.getMarketId(), null);
        if(inputList.size()>1){
            int minStoreNum = 0, maxStoreNum = -1;
            for(int i  = 1;i<inputList.size();i += 2){
                switch (inputList.get(i)){
                    case "-m":
                        //限定超市
                        storeBo.setMarketId(inputList.get(i+1));
                        break;
                    case "-p":
                        //限定商品
                        storeBo.setProductId(inputList.get(i+1));
                        break;
                    case "-min":
                        //限定最小库存数
                        if(InputUtils.isNumString(inputList.get(i+1))){
                            minStoreNum = Integer.parseInt(inputList.get(i+1));
                        }else {
                            System.out.println("[-min]参数不能转换为数字");
                        }
                        break;
                    case "-max":
                        //限定最大库存数
                        if(InputUtils.isNumString(inputList.get(i+1))){
                            maxStoreNum = Integer.parseInt(inputList.get(i+1));
                        }else {
                            System.out.println("[-max]参数不能转换为数字");
                        }
                        break;
                    default:
                        System.out.println("存在异常参数");
                }
            }
            List<Store> storeList = storeService.queryStoreByQueryBo(storeBo);
            List<Store> returnList = new ArrayList<>();
            for(int index = storeList.size()-1;index >= 0;index--){
                int indexOrderNum = storeList.get(index).getStoreNum();
                if(maxStoreNum == -1 && indexOrderNum > minStoreNum){
                    returnList.add(storeList.get(index));
                    continue;
                }
                if(maxStoreNum != -1 && indexOrderNum < maxStoreNum && indexOrderNum > minStoreNum){
                    returnList.add(storeList.get(index));
                }
            }
            return ListUtils.storeListString(returnList);
        }
        return ListUtils.storeListString(storeService.queryStoreByQueryBo(storeBo));
    }

    public static String marketViewInput(View view, List<String> inputList){
        //添加库存
        if(inputList.size()>1 && Constant.INSERT_ARG.equals(inputList.get(1))){
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员进行操作";
            }
            if(inputList.size()%2!=0){
                return "输入格式错误或参数缺失";
            }
            String productId = null;
            int storeNum = -1;
            for(int i  = 2;i<inputList.size();i += 2){
                if("-p".equals(inputList.get(i))){
                    productId = inputList.get(i+1);
                }
                if("-n".equals(inputList.get(i))){
                    if(!InputUtils.isNumString(inputList.get(i+1))){
                        return "必需的[-n]参数不能转换为数字";
                    }
                    storeNum = Integer.parseInt(inputList.get(i+1));
                }
            }
            if(productId == null || storeNum <= 0){
                return "添加库存需要指定商品id,且添加数量必须大于0";
            }
            if(new ProductServiceImpl().queryProductByProductId(productId)==null){
                return "商品不存在";
            }
            if(storeService.queryStoreByQueryBo(new QueryStoreBo(view.getMarketId(), productId)).isEmpty()){
                if(storeService.insertStore(view.getMarketId(), productId, storeNum)){
                    return "超市库存添加成功";
                }
                return "超市库存添加失败";
            }
            //超市中已存在该商品库存
            Store store = storeService.queryStoreByQueryBo(new QueryStoreBo(view.getMarketId(), productId)).get(0);
            store.setStoreNum(store.getStoreNum() + storeNum);
            if(storeService.updateStore(store)){
                return "超市中已有该商品,库存添加成功";
            }
            return "库存更新失败";
        }
        //删除库存
        if(inputList.size()>2 && Constant.DELETE_ARG.equals(inputList.get(1))){
            if(!viewService.checkUser(view)){
                return "权限不足，请联系该超市管理员进行操作";
            }
            if(storeService.deleteStore(view.getMarketId(), inputList.get(inputList.size()-1))){
                return "库存:"+inputList.get(inputList.size()-1)+"已删除";
            }
            return "库存删除失败";
        }
        //查询库存
        return queryStore(view, inputList);
    }
}
