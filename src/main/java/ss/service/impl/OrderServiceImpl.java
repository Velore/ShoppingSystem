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
    public boolean isOrderAllCheck(String marketId) {
        List<Order> orderList = queryOrderByQueryOrderBo(
                new QueryOrderBo(null, marketId, null));
        //超市没有订单
        if(orderList.isEmpty()){
            return true;
        }
        for(Order o : orderList){
            //存在订单未被处理
            if(o.getOrderStatus() == 0){
                return false;
            }
        }
        //订单已全部被处理,或订购者取消
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
        //查询订单对应的库存
        Store store = storeService.queryStoreByQueryBo(
                new QueryStoreBo(order.getMarketId(), order.getProductId())).get(0);
        if(store.getStoreNum()>=order.getOrderNum()){
            store.setStoreNum(store.getStoreNum()-order.getOrderNum());
            //库存足够,更新订单状态为完成
            order.setOrderStatus(1);
        }else {
            //库存不足,更新订单状态为失败
            order.setOrderStatus(2);
        }
        //同时更新订单和对应的库存
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
