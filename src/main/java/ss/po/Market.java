package ss.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Velore
 * @date 2022/1/2
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Market {

    private String marketId;

    private String marketName;

    private String userId;
}
