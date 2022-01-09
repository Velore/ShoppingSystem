package ss.service.impl;

import org.apache.ibatis.session.SqlSession;
import ss.bo.QueryOrderBo;
import ss.bo.QueryStoreBo;
import ss.dao.OrderMapper;
import ss.po.Order;
import ss.po.Store;
import ss.service.OrderService;
import ss.service.StoreService;
import ss.utils.MybatisUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public class OrderServiceImpl implements OrderService {

    SqlSession session = MybatisUtils.getSqlSession();

    OrderMapper mapper = session.getMapper(OrderMapper.class);

    @Override
    public boolean checkOrder(String marketId) {
        List<Order> orderList = queryOrderByQueryOrderBo(
                new QueryOrderBo(null, marketId, null));
        for(Order o : orderList){
            if(o.getOrderStatus() == 0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean insertOrder(Order order) {
        if(mapper.insertOrder(order) == 1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOrder(Order order){
        StoreService storeService = new StoreServiceImpl();
        Store store = storeService.queryStoreByQueryBo(new QueryStoreBo(order.getMarketId(), order.getProductId())).get(0);
        if(store.getStoreNum()>order.getOrderNum()){
            store.setStoreNum(store.getStoreNum()-order.getOrderNum());
        }
        if(mapper.updateOrder(order) == 1 && storeService.updateStore(store)){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String orderId) {
        if(mapper.deleteOrder(orderId) == 1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public List<Order> queryAllOrder() {
        return mapper.queryAllOrder();
    }

    @Override
    public Order queryOrderByOrderId(String orderId) {
        return mapper.queryOrderByOrderId(orderId);
    }

    @Override
    public List<Order> queryOrderByQueryOrderBo(QueryOrderBo queryOrderBo) {
        return mapper.queryOrderByQueryOrderBo(
                queryOrderBo.getUserId(),
                queryOrderBo.getMarketId(),
                queryOrderBo.getProductId());
    }
}
