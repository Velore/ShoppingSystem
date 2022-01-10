package ss.controller;

import ss.bo.QueryStoreBo;
import ss.constant.Constant;
import ss.po.Product;
import ss.po.View;
import ss.service.ProductService;
import ss.service.StoreService;
import ss.service.ViewService;
import ss.service.impl.ProductServiceImpl;
import ss.service.impl.StoreServiceImpl;
import ss.service.impl.ViewServiceImpl;
import ss.utils.ListUtils;
import ss.utils.RandomUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class ProductController {

    static ProductService productService = new ProductServiceImpl();
    static StoreService storeService = new StoreServiceImpl();
    static ViewService viewService = new ViewServiceImpl();

    public static String viewInput(View view, List<String> inputList){
        //查询商品
        //没有限定参数时,返回全部商品
        if(inputList.size() == 1){
            return ListUtils.productListString(productService.queryAllProduct());
        }
        //查询参数限定商品名
        if(inputList.size() == 2 && !"-d".equals(inputList.get(1))){
            return ListUtils.productListString(productService.queryProductLikeName(inputList.get(1)));
        }
        //对商品进行增删改需要root用户
        if(!viewService.checkUser(view)){
            return "权限不足，请联系root用户进行操作";
        }
        //添加新商品
        if(inputList.size()>1 && Constant.INSERT_ARG.equals(inputList.get(1))){
            if(inputList.size()%2!=0){
                return "输入格式错误或参数缺失";
            }
            String productName = null, description = null;
            for(int i = 2;i<inputList.size();i += 2){
                if("-n".equals(inputList.get(i))){
                    productName = inputList.get(i+1);
                }
                if("-d".equals(inputList.get(i))){
                    description = inputList.get(i+1);
                }
            }
            if(productName!=null && productService.insertProduct(
                    new Product(
                            RandomUtils.intString(Constant.DEFAULT_ID_LENGTH),
                            productName, description))){
                return "新商品插入成功";
            }
        }
        //修改商品信息
        if(inputList.size()>1 && Constant.ALTER_ARG.equals(inputList.get(1))){
            if(inputList.size()%2!=0){
                return "输入格式错误或参数缺失";
            }
            Product product = new Product();
            for(int i = 2;i<inputList.size();i += 2){
                switch (inputList.get(i)){
                    case "-i":
                        product.setProductId(inputList.get(i+1));
                        break;
                    case "-n":
                        product.setProductName(inputList.get(i+1));
                        break;
                    case "-d":
                        product.setDescription(inputList.get(i+1));
                        break;
                    default:
                        System.out.println("存在异常参数");
                }
            }
            if(productService.updateProduct(product)){
                return "商品修改成功";
            }
            return "商品修改失败";
        }
        //可以在-d后输入多个参数,但只有最后一个参数生效,作为商品id
        if(inputList.size()>2 && "-d".equals(inputList.get(1))){
            if(!storeService.queryStoreByQueryBo(
                    new QueryStoreBo(null, inputList.get(inputList.size()-1))).isEmpty()){
                return "仍有超市存在该商品库存,不可删除该商品";
            }
            if(productService.deleteProduct(inputList.get(inputList.size()-1))){
                return "商品删除成功";
            }
            return "商品删除失败";
        }
        return "命令无法识别";
    }
}
