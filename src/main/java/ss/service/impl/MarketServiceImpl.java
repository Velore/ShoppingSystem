package ss.service.impl;

import ss.bo.QueryMarketBo;
import ss.constant.Constant;
import ss.dao.MarketMapper;
import ss.po.Market;
import ss.service.MarketService;
import ss.utils.MybatisUtils;
import ss.utils.RandomUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public class MarketServiceImpl implements MarketService {

    @Override
    public boolean insertMarket(String marketName, String userId) {
        MarketMapper mapper = MybatisUtils.getMapper(MarketMapper.class);
        return mapper.insertMarket(new Market(
                RandomUtils.intString(Constant.DEFAULT_ID_LENGTH),
                marketName,
                userId)) == 1;
    }

    @Override
    public boolean updateMarket(Market market) {
        MarketMapper mapper = MybatisUtils.getMapper(MarketMapper.class);
        if(mapper.queryMarketByMarketId(market.getMarketId()) == null){
            return false;
        }
        return mapper.updateMarket(market) == 1;
    }

    @Override
    public boolean deleteMarket(String marketId) {
        MarketMapper mapper = MybatisUtils.getMapper(MarketMapper.class);
        return mapper.deleteMarket(marketId) == 1;
    }

    @Override
    public List<Market> queryAllMarket() {
        MarketMapper mapper = MybatisUtils.getMapper(MarketMapper.class);
        return mapper.queryAllMarket();
    }

    @Override
    public Market queryMarketByMarketId(String marketId) {
        MarketMapper mapper = MybatisUtils.getMapper(MarketMapper.class);
        return mapper.queryMarketByMarketId(marketId);
    }

    @Override
    public List<Market> queryMarketByQueryMarketBo(QueryMarketBo queryMarketBo) {
        MarketMapper mapper = MybatisUtils.getMapper(MarketMapper.class);
//        没有查询限定条件
        if(queryMarketBo.getMarketName() == null && queryMarketBo.getUserId() == null){
            return mapper.queryAllMarket();
        }
//        查询限定条件为名字
        if(queryMarketBo.getMarketName()!=null && queryMarketBo.getUserId() == null){
            return mapper.queryMarketLikeName(queryMarketBo.getMarketName());
        }
//        查询限定条件为超市管理员id
        if(queryMarketBo.getMarketName()==null && queryMarketBo.getUserId() != null){
            return mapper.queryMarketByUserId(queryMarketBo.getUserId());
        }
//        多个查询限定条件
        List<Market> mList1 = mapper.queryMarketLikeName(queryMarketBo.getMarketName());
        List<Market> mList2 = mapper.queryMarketByUserId(queryMarketBo.getUserId());
        mList1.retainAll(mList2);
        return mList1;
    }
}
