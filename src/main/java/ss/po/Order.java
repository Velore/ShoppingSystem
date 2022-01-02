package ss.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
