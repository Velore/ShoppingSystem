package ss.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Velore
 * @date 2022/1/8
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryMarketBo {

    private String marketName;

    private String userId;
}
