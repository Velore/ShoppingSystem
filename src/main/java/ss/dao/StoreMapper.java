package ss.dao;

import org.apache.ibatis.annotations.Param;
import ss.po.Store;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public interface StoreMapper {

    /**
     * 插入新的超市存货
     * @param marketId marketId
     * @param productId productId
     * @param storeNum storeNum
     * @return 是否插入成功
     */
    int insertStore(@Param("marketId") String marketId,
                    @Param("productId") String productId,
                    @Param("storeNum") int storeNum);

    /**
     * 更新超市存货
     * @param store store
     * @return 是否更新成功
     */
    int updateStore(Store store);

    /**
     * 删除超市存货
     * @param marketId marketId
     * @param productId productId
     * @return 是否删除成功
     */
    int deleteStore(@Param("marketId") String marketId,
                    @Param("productId") String productId);

    /**
     * 根据超市id查询
     * @param marketId id
     * @return marketStore list
     */
    List<Store> queryStoreByMarketId(String marketId);

    /**
     * 根据商品id查询
     * @param productId id
     * @return marketStore list
     */
    List<Store> queryStoreByProductId(String productId);
}
