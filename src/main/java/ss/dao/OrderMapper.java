package ss.dao;

import org.apache.ibatis.annotations.Param;
import ss.po.Order;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public interface OrderMapper {

    /**
     * 插入新的订单
     * @param order order
     * @return 是否插入成功
     */
    int insertOrder(Order order);

    /**
     * 更新订单
     * @param order order
     * @return 是否更新成功
     */
    int updateOrder(Order order);

    /**
     * 删除订单
     * @param orderId id
     * @return 是否删除成功
     */
    int deleteOrder(String orderId);

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
     * @param userId userId
     * @param marketId marketId
     * @param productId productId
     * @return order list
     */
    List<Order> queryOrderByQueryOrderBo(@Param("userId") String userId,
                                         @Param("marketId") String marketId,
                                         @Param("productId") String productId);
}
