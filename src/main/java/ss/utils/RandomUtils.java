package ss.utils;

/**
 * 随机数生成类
 * @author Velore
 * @date 2022/1/8
 **/
public class RandomUtils {

    /**
     * @return 生成随机数字(0,5)
     */
    public static int randomInt() {
        return (int)(Math.random()*5);
    }

    /**
     * @param n 随机数字的右边界
     * @return 生成随机数字(0,n)
     */
    public static int randomInt(int n) {
        return (n <= 0) ? -1 : (int)(Math.random()*n);
    }

    /**
     * @param min 随机数字的左边界
     * @param max 随机数字的右边界
     * @return 生成随机数字(min,max)
     */
    public static int randomInt(int min, int max){
        return (min <= 0 || max <= 0 || min > max) ? -1 : (int)(Math.random()*(max-min)+min);
    }

    /**
     * @return 生成随机小写字母
     */
    public static char lowerChar() {
        return (char)(Math.random()*26+97);
    }

    /**
     * @return 生成随机大写字母
     */
    public static char capitalChar() {
        return (char)(Math.random()*26+65);
    }

    /**
     * @param n 生成字符串的位数
     * @return n位随机（大小写字母，数字混合）字符串
     */
    public static String mixString(int n) {
        char[] prevStr = new char[n];
        for(int j = 0; j < n; j++){
//            每一位随机数的随机因子root
            int root = (int)(Math.random()*3);
            if(root == 0){
                prevStr[j] = (char)(Math.random()*10+48);
            }else if(root == 1){
                prevStr[j] = lowerChar();
            }else{
                prevStr[j] = capitalChar();
            }
        }
        return new String(prevStr);
    }

    /**
     * 生成纯数字随机数
     * @param n 随机数的位数
     * @return 随机数
     */
    public static String intString(int n){
        char[] pervStr = new char[n];
        for(int i = 0;i < n;i++){
            pervStr[i] = (char)(Math.random()*10+48);
        }
        return new String(pervStr);
    }
}
