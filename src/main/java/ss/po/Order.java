package ss.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ss.constant.Constant;
import ss.utils.RandomUtils;

/**
 * @author Velore
 * @date 2022/1/2
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String orderId;

    private String userId;

    private String marketId;

    private String productId;

    private int orderNum;

    private int orderStatus;

    public Order(String userId, String marketId, String productId, int orderNum){
        this.orderId = RandomUtils.intString(Constant.DEFAULT_ID_LENGTH);
        this.userId = userId;
        this.marketId = marketId;
        this.productId = productId;
        this.orderNum = orderNum;
        this.orderStatus = 0;
    }

}
