package ss.service.impl;

import org.apache.ibatis.session.SqlSessionManager;
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

    @Override
    public boolean insertStore(String marketId, String productId, int storeNum) {
        StoreMapper mapper = MybatisUtils.getMapper(StoreMapper.class);
        return mapper.insertStore(marketId, productId, storeNum) == 1;
    }

    @Override
    public boolean updateStore(Store store) {
        StoreMapper mapper = MybatisUtils.getMapper(StoreMapper.class);
        return mapper.updateStore(store) == 1;
    }

    @Override
    public boolean deleteStore(String marketId, String productId) {
        StoreMapper mapper = MybatisUtils.getMapper(StoreMapper.class);
        return mapper.deleteStore(marketId, productId) == 1;
    }

    @Override
    public boolean deleteAllStoreByMarketId(String marketId) {
        StoreMapper mapper = MybatisUtils.getMapper(StoreMapper.class);
        List<Store> storeList = queryStoreByQueryBo(new QueryStoreBo(marketId, null));
        for(Store s : storeList){
            if(mapper.deleteStore(marketId, s.getProductId())!=1){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Store> queryStoreByQueryBo(QueryStoreBo queryBo) {
        SqlSessionManager manager = MybatisUtils.getSessionManager();
        manager.startManagedSession();
        StoreMapper mapper = MybatisUtils.getMapper(StoreMapper.class);
        if(queryBo.getMarketId() == null && queryBo.getProductId() != null){
            return mapper.queryStoreByProductId(queryBo.getProductId());
        }
        if(queryBo.getMarketId() != null && queryBo.getProductId() == null){
            return mapper.queryStoreByMarketId(queryBo.getMarketId());
        }
        List<Store> sList1 = mapper.queryStoreByMarketId(queryBo.getMarketId());
        List<Store> sList2 = mapper.queryStoreByProductId(queryBo.getProductId());
        manager.commit();
        sList1.retainAll(sList2);
        return sList1;
    }
}
