package ss.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class InputUtils {

    /**
     * 匹配多个中文字符,大小写字母和数字和某些标点符号;
     */
    public static final Pattern MSG_PATTERN = Pattern.compile("([\\u4e00-\\u9fa5_a-zA-Z0-9\\p{P}]+)");

    /**
     * 判断输入的字符串是否可以被转换为数字
     * @param s s
     * @return 可以被转换为数字则返回true
     */
    public static boolean isNumString(String s){
        for (int i = 0 ;i<s.length();i++){
            if(s.charAt(i)<48 || s.charAt(i) > 58){
                return false;
            }
        }
        return true;
    }

    /**
     * 将用户输入的[字符串]通过正则匹配分割为[命令序列];
     * @param input 要切割的输入字符串
     * @param pattern 正则匹配的匹配规则
     * @return 完成分割的字符串数组prevInput
     */
    public static ArrayList<String> inputSplit(String input, Pattern pattern){
        ArrayList<String> prevInput = new ArrayList<>();
//        去除输入两端的空格后进行正则匹配
        Matcher m = pattern.matcher(input.trim());
        while(m.find()){
//            将正则匹配后得到的结果返回数组
            prevInput.add(m.group());
        }
        return prevInput;
    }

    /**
     * 默认分割格式
     * @param input input
     * @return list
     */
    public static ArrayList<String> defaultInputSplit(String input){
        ArrayList<String> prevInput = new ArrayList<>();
//        去除输入两端的空格后进行正则匹配
        Matcher m = MSG_PATTERN.matcher(input.trim());
        while(m.find()){
//            将正则匹配后得到的结果返回数组
            prevInput.add(m.group());
        }
        return prevInput;
    }
}
