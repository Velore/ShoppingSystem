import org.junit.jupiter.api.Test;
import ss.bo.QueryOrderBo;
import ss.po.Order;
import ss.po.Store;
import ss.service.OrderService;
import ss.service.impl.OrderServiceImpl;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public class OrderTest {

    OrderService service = new OrderServiceImpl();

    @Test
    public void insertOrderTest(){
        Order order = new Order("u1", "mb", "pa", 10);
        System.out.println(service.insertOrder(order));
    }

    @Test
    public void updateOrderTest(){
        Order order = new Order("u1", "ma", "pa", 300);
        order.setOrderId("1001");
        order.setOrderStatus(1);
        System.out.println(service.updateOrder(order));
    }

    @Test
    public void deleteOrderTest(){
        System.out.println(service.deleteOrder("test_id"));
    }

    @Test
    public void queryAllOrderTest(){
        List<Order> orderList = service.queryAllOrder();
        for(Order o : orderList){
            System.out.println(o.toString());
        }
    }

    @Test
    public void queryOrderByOrderIdTest(){
        System.out.println(service.queryOrderByOrderId("1001"));
    }

    @Test
    public void queryOrderByQueryBo(){
        QueryOrderBo bo = new QueryOrderBo("u1", null, null);
        List<Order> oList = service.queryOrderByQueryOrderBo(bo);
        for(Order o : oList){
            System.out.println(o.toString());
        }
    }
}
