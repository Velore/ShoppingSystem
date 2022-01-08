package ss.service.impl;

import org.apache.ibatis.session.SqlSession;
import ss.bo.QueryStoreBo;
import ss.dao.StoreMapper;
import ss.po.Store;
import ss.service.StoreService;
import ss.utils.MybatisUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public class StoreServiceImpl implements StoreService {

    SqlSession session = MybatisUtils.getSqlSession();

    StoreMapper mapper = session.getMapper(StoreMapper.class);

    @Override
    public boolean insertStore(String marketId, String productId, int storeNum) {
        if(mapper.insertStore(marketId, productId, storeNum) == 1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateStore(Store store) {
        if(mapper.updateStore(store) == 1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteStore(String marketId, String productId) {
        if(mapper.deleteStore(marketId, productId) == 1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public List<Store> queryStoreByQueryBo(QueryStoreBo queryBo) {
        if(queryBo.getMarketId() == null && queryBo.getProductId() != null){
            return mapper.queryStoreByProductId(queryBo.getProductId());
        }
        if(queryBo.getMarketId() != null && queryBo.getProductId() == null){
            return mapper.queryStoreByProductId(queryBo.getMarketId());
        }
        List<Store> sList1 = mapper.queryStoreByMarketId(queryBo.getMarketId());
        List<Store> sList2 = mapper.queryStoreByProductId(queryBo.getProductId());
        sList1.retainAll(sList2);
        return sList1;
    }
}