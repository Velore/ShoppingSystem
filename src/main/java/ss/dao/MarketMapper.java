package ss.dao;

import ss.po.Market;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public interface MarketMapper {

    /**
     * 插入新的超市
     * @param market market
     * @return 是否插入成功
     */
    int insertMarket(Market market);

    /**
     * 修改超市信息
     * @param market market
     * @return 是否修改成功
     */
    int updateMarket(Market market);

    /**
     * 删除超市
     * @param marketId id
     * @return 是否删除成功
     */
    int deleteMarket(String marketId);

    /**
     * 查询全部超市
     * @return market list
     */
    List<Market> queryAllMarket();

    /**
     * 根据id查询超市
     * @param marketId id
     * @return market
     */
    Market queryMarketByMarketId(String marketId);

    /**
     * 根据超市管理员id查询超市
     * @param userId user id
     * @return market list
     */
    List<Market> queryMarketByUserId(String userId);

    /**
     * 根据名字模糊查询超市
     * @param marketName name
     * @return market list
     */
    List<Market> queryMarketLikeName(String marketName);
}
