package ss.service.impl;

import org.apache.ibatis.session.SqlSession;
import ss.bo.QueryOrderBo;
import ss.dao.OrderMapper;
import ss.po.Order;
import ss.service.OrderService;
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
    public boolean insertOrder(Order order) {
        if(mapper.insertOrder(order) == 1){
            session.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOrder(Order order){
        if(mapper.updateOrder(order) == 1){
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
