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
public class User {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 密码
     */
    private String password;
}
