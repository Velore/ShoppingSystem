package ss.service;

import ss.bo.QueryStoreBo;
import ss.po.Store;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public interface StoreService {

    /**
     * 插入新的超市存货
     * @param marketId marketId
     * @param productId productId
     * @param storeNum storeNum
     * @return 是否插入成功
     */
    boolean insertStore(String marketId, String productId, int storeNum);

    /**
     * 更新超市存货
     * @param store store
     * @return 是否更新成功
     */
    boolean updateStore(Store store);

    /**
     * 删除超市存货
     * @param marketId marketId
     * @param productId productId
     * @return 是否删除成功
     */
    boolean deleteStore(String marketId, String productId);

    /**
     * 根据超市id删除该超市全部库存
     * @param marketId id
     * @return 是否删除成功
     */
    boolean deleteAllStoreByMarketId(String marketId);

    /**
     * 根据查询限定条件查询
     * @param queryBo queryBo
     * @return marketStore list
     */
    List<Store> queryStoreByQueryBo(QueryStoreBo queryBo);
}
