import org.junit.jupiter.api.Test;
import ss.bo.QueryStoreBo;
import ss.po.Store;
import ss.service.StoreService;
import ss.service.impl.StoreServiceImpl;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public class StoreTest {

    StoreService service = new StoreServiceImpl();

    @Test
    public void insertStoreTest(){
        System.out.println(service.insertStore("ma", "pa", 10));
    }

    @Test
    public void updateStoreTest(){
        QueryStoreBo bo = new QueryStoreBo("ma", "pa");
        Store store = service.queryStoreByQueryBo(bo).get(0);
        System.out.println(store.toString());
        store.setStoreNum(20);
        System.out.println(service.updateStore(store));
        System.out.println(service.queryStoreByQueryBo(bo).get(0));
    }

    @Test
    public void deleteStoreTest(){
        System.out.println(service.deleteStore("ma", "pa"));
    }

    @Test
    public void queryStoreByQueryBoTest(){
        QueryStoreBo bo1 = new QueryStoreBo("m", null);
        List<Store> storeList = service.queryStoreByQueryBo(bo1);
        for(Store s : storeList){
            System.out.println(s.toString());
        }
    }
}
