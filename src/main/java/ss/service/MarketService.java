package ss.service;

import ss.bo.QueryMarketBo;
import ss.po.Market;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/7
 **/
public interface MarketService {

    /**
     * 插入新的超市
     * @param market market
     * @return 是否插入成功
     */
    boolean insertMarket(Market market);

    /**
     * 修改超市信息
     * @param market market
     * @return 是否修改成功
     */
    boolean updateMarket(Market market);

    /**
     * 删除超市
     * @param marketId id
     * @return 是否删除成功
     */
    boolean deleteMarket(String marketId);

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
     * 根据查询Bo查询超市
     * @param queryMarketBo 查询Bo,包含两个属性:超市名字和超市管理员
     * @return market list
     */
    List<Market> queryMarketByQueryMarketBo(QueryMarketBo queryMarketBo);
}
