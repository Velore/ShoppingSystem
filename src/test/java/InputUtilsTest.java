import org.junit.jupiter.api.Test;
import ss.utils.InputUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class InputUtilsTest {

    @Test
    public void isNumStringTest(){
        System.out.println(InputUtils.isNumString("12s"));
        System.out.println(InputUtils.isNumString("125"));
        System.out.println(InputUtils.isNumString("1d2"));
        System.out.println(InputUtils.isNumString("s12"));
    }

    @Test
    public void inputSpiltTest(){
        String input = "user -n 名字1 -p 密码1";
        List<String> list = InputUtils.inputSplit(input, InputUtils.MSG_PATTERN);
        for(String s : list){
            System.out.println(s);
        }
    }
}
