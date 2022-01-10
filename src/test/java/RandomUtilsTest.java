import org.junit.jupiter.api.Test;
import ss.utils.RandomUtils;

/**
 * @author Velore
 * @date 2022/1/11
 **/
public class RandomUtilsTest {

    @Test
    public void randomIntTest(){
        for (int i = 0 ;i<10;i++){
            System.out.println(RandomUtils.randomInt());
        }
    }

    @Test
    public void randomIntWithRightRangeTest(){
        for (int i = 0 ;i<10;i++){
            System.out.println(RandomUtils.randomInt(20));
        }
    }

    @Test
    public void randomIntWithLeftAndRightRangeTest(){
        for (int i = 0 ;i<10;i++){
            System.out.println(RandomUtils.randomInt(10, 20));
        }
    }

    @Test
    public void minStringTest(){
        for (int i = 0 ;i<10;i++){
            System.out.println(RandomUtils.mixString(10));
        }
    }
}
