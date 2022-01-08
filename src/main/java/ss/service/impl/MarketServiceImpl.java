package ss.service.impl;

import org.apache.ibatis.session.SqlSession;
import ss.bo.QueryMarketBo;
import ss.dao.MarketMapper;
import ss.po.Market;
import ss.service.MarketService;
import ss.utils.MybatisUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public class MarketServiceImpl implements MarketService {

    SqlSession session = MybatisUtils.getSqlSession();

    MarketMapper mapper = session.getMapper(MarketMapper.class);

    @Override
    public boolean insertMarket(Market market) {
        if(mapper.queryMarketByMarketId(market.getMarketId()) == null){
            if(mapper.insertMarket(market) == 1){
                session.commit();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateMarket(Market market) {
        if(mapper.queryMarketByMarketId(market.getMarketId()) == null){
            return false;
        }
        if(mapper.updateMarket(market) == 1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMarket(String marketId) {
        if(mapper.deleteMarket(marketId)==1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public List<Market> queryAllMarket() {
        return mapper.queryAllMarket();
    }

    @Override
    public Market queryMarketByMarketId(String marketId) {
        return mapper.queryMarketByMarketId(marketId);
    }

    @Override
    public List<Market> queryMarketByQueryMarketBo(QueryMarketBo queryMarketBo) {
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
