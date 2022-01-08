package ss.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Velore
 * @date 2022/1/7
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryOrderBo {

    private String userId;

    private String marketId;

    private String productId;
}
