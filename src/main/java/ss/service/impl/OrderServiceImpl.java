package ss.service.impl;

import org.apache.ibatis.session.SqlSessionManager;
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
        OrderMapper mapper = MybatisUtils.getMapper(OrderMapper.class);
        return mapper.insertOrder(order) == 1;
    }

    @Override
    public boolean updateOrder(Order order){
        SqlSessionManager manager = MybatisUtils.getSessionManager();
        //开启手动管理事务
        manager.startManagedSession();
        OrderMapper mapper = MybatisUtils.getMapper(OrderMapper.class);
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
            manager.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteOrder(String orderId) {
        OrderMapper mapper = MybatisUtils.getMapper(OrderMapper.class);
        return mapper.deleteOrder(orderId) == 1;
    }

    @Override
    public List<Order> queryAllOrder() {
        OrderMapper mapper = MybatisUtils.getMapper(OrderMapper.class);
        return mapper.queryAllOrder();
    }

    @Override
    public Order queryOrderByOrderId(String orderId) {
        OrderMapper mapper = MybatisUtils.getMapper(OrderMapper.class);
        return mapper.queryOrderByOrderId(orderId);
    }

    @Override
    public List<Order> queryOrderByQueryOrderBo(QueryOrderBo queryOrderBo) {
        OrderMapper mapper = MybatisUtils.getMapper(OrderMapper.class);
        return mapper.queryOrderByQueryOrderBo(
                queryOrderBo.getUserId(),
                queryOrderBo.getMarketId(),
                queryOrderBo.getProductId());
    }
}
