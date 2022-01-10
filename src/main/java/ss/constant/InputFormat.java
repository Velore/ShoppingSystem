package ss.constant;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class InputFormat {

    public static final Map<String, String> MAIN_VIEW = new LinkedHashMap<>();

    public static final Map<String, String> USER_VIEW = new LinkedHashMap<>();

    public static final Map<String, String> MARKET_VIEW = new LinkedHashMap<>();

    static {

        //以下为系统主界面
        MAIN_VIEW.put("\n用户", "-------------------------------------");
        MAIN_VIEW.put("1.用户注册", "reg [userId](必填) [password](必填)");
        MAIN_VIEW.put("2.用户登录", "login [-i userId](必填) [-p password](必填)");

        MAIN_VIEW.put("\n获取帮助","help");
        MAIN_VIEW.put("查看对输入命令的介绍", "guide");
        MAIN_VIEW.put("退出系统", "exit");

        //以下为用户界面
        USER_VIEW.put("\n用户", "--------------------------------------");
        USER_VIEW.put("1.修改[当前]用户信息","user alter [-n username] [-p password]");
        USER_VIEW.put("2.查询[当前]用户信息","user");
        USER_VIEW.put("3.查询[全部]用户信息(root用户)","user");

        USER_VIEW.put("\n订单", "--------------------------------------");
        USER_VIEW.put("a.查询[当前]用户订单(可限定超市id或商品id)","order [-m marketId] [-p productId]");
        USER_VIEW.put("b.修改[当前]用户订单的状态为[取消]","order cancel [orderId](必填)");

        USER_VIEW.put("\n库存", "--------------------------------------");
        USER_VIEW.put("c.查询商品库存,至少存在超市id和商品id两者其中一个限定条件,还可限定最大库存数量,最小库存数量",
                "store [-m marketId] [-p productId] [-min 最小库存数] [-max 最大库存数]");

        USER_VIEW.put("\n超市", "--------------------------------------");
        USER_VIEW.put("1.查询[多个]超市(默认为[当前]用户拥有的,可限定超市名字或超市管理员)",
                "market [-n marketName] [-u userId]");
        USER_VIEW.put("2.查询[全部]超市", "market [-all](必填)");
        USER_VIEW.put("3.新增超市,指定[当前]用户为超市管理员", "market ins [marketName](必填)");
        USER_VIEW.put("4.删除[指定]超市,-d后的参数允许输入多个,但只有最后一个作为marketId", "market -d [marketId](必填)");
        USER_VIEW.put("5.进入[指定]超市", "into [marketId](必填)");

        USER_VIEW.put("\n商品", "--------------------------------------");
        USER_VIEW.put("a.查询[多个]商品(默认查询全部商品,可限定商品名)", "prod [productName]");
        USER_VIEW.put("b.新增商品(root用户)", "prod ins [-n productName](必填) [-d description]");
        USER_VIEW.put("c.修改[指定]商品信息(root用户)",
                "prod alter [-i productId](必填) [-n productName] [-d description]");
        USER_VIEW.put("d.删除[指定]商品(root用户),-d后的参数允许输入多个,但只有最后一个作为productId",
                "prod -d [productId](必填)");

        USER_VIEW.put("\n获取帮助","help");
        USER_VIEW.put("查看对输入命令的介绍", "guide");
        USER_VIEW.put("返回系统主界面","exit");
        USER_VIEW.put("退出程序","shutdown");

        //以下为超市界面
        MARKET_VIEW.put("\n超市", "--------------------------------------");
        MARKET_VIEW.put("a.查询[当前]超市的信息", "market");
        MARKET_VIEW.put("b.查询[多个]超市(默认为[当前]用户拥有的,可限定超市名字或超市管理员)",
                "market [-n marketName] [-u userId]");
        MARKET_VIEW.put("c.查询[全部]超市", "market [-all](必填)");
        MARKET_VIEW.put("d.修改[当前]超市", "market alter [-n marketName] [-u userId]");

        MARKET_VIEW.put("\n商品", "--------------------------------------");
        MARKET_VIEW.put("1.查询[多个]商品(默认查询全部商品,可限定商品名)", "prod [-n productName]");
        MARKET_VIEW.put("2.新增商品(root用户)", "prod ins [-n productName](必填) [-d description]");
        MARKET_VIEW.put("3.修改[指定]商品信息(root用户)",
                "prod alter [-i productId](必填) [-n productName] [-d description]");
        MARKET_VIEW.put("4.删除[指定]商品(root用户)", "prod -d [productId](必填)");

        MARKET_VIEW.put("\n库存", "--------------------------------------");
        MARKET_VIEW.put("c.查询[多个]库存,默认[当前]超市全部库存,可限定其他超市,商品,最大或者最小库存",
                "store [-m marketId] [-p productId] [-min 最小库存数] [-max 最大库存数]");
        MARKET_VIEW.put("a.新增[当前]超市库存,若库存已存在,则更新库存(数量增加)",
                "store ins [-p productId](必填) [-n storeNum](必填)");
        MARKET_VIEW.put("b.删除[指定]库存,-d后的参数允许输入多个,但只有最后一个作为productId", "store -d [productId](必填)");

        MARKET_VIEW.put("\n订单", "--------------------------------------");
        MARKET_VIEW.put("1.查询[当前]超市订单,可限定订购用户,商品,订购数量和订单状态",
                "order [-u userId] [-p productId] [-min 最小订购数] [-max 最大订购数] [-s orderStatus]");
        MARKET_VIEW.put("2.新增[当前]超市订单", "order ins [-p productId](必填) [-n orderNum](必填)");
        MARKET_VIEW.put("3.检查[当前]超市订单,更新订单状态,若库存足够则返回[订单完成],否则返回[订单失败]", "order check [orderId]");

        MARKET_VIEW.put("\n获取帮助","help");
        MARKET_VIEW.put("查看对输入命令的介绍", "guide");
        MARKET_VIEW.put("返回用户界面","exit");
        MARKET_VIEW.put("退出程序","shutdown");
    }
}
