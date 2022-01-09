package ss.service;

import ss.bo.QueryOrderBo;
import ss.po.Order;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/7
 **/
public interface OrderService {

    /**
     * 检查超市是否存在未处理订单
     * @param marketId id
     * @return 存在返回true
     */
    boolean checkOrder(String marketId);

    /**
     * 插入新的订单
     * @param order order
     * @return 是否插入成功
     */
    boolean insertOrder(Order order);

    /**
     * 更新订单
     * @param order order
     * @return 是否更新成功
     */
    boolean updateOrder(Order order);

    /**
     * 删除订单
     * @param orderId id
     * @return 是否删除成功
     */
    boolean deleteOrder(String orderId);

    /**
     * 查询全部订单
     * @return order list
     */
    List<Order> queryAllOrder();

    /**
     * 根据id查询订单
     * @param orderId id
     * @return order
     */
    Order queryOrderByOrderId(String orderId);

    /**
     * 根据查询限定条件查询订单
     * @param queryOrderBo bo
     * @return order list
     */
    List<Order> queryOrderByQueryOrderBo(QueryOrderBo queryOrderBo);
}
