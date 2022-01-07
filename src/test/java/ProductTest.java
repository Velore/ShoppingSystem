import org.junit.jupiter.api.Test;
import ss.po.Product;
import ss.service.ProductService;
import ss.service.impl.ProductServiceImpl;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/7
 **/
public class ProductTest {

    final ProductService productService = new ProductServiceImpl();

    @Test
    public void insertProductTest(){
        Product product = new Product("test_id", "test_name", "test_desc");
        System.out.println(productService.insertProduct(product));
    }

    @Test
    public void updateProductTest(){
        Product product = new Product("test_id", "test_name1", "test_desc");
        System.out.println(productService.updateProduct(product));
    }

    @Test
    public void deleteProductTest(){
        System.out.println(productService.queryAllProduct());
        System.out.println(productService.deleteProduct("test_id"));
        System.out.println(productService.queryAllProduct());
    }

    @Test
    public void queryAllProductTest(){
        List<Product> productList = productService.queryAllProduct();
        for(Product p : productList){
            System.out.println(p.toString());
        }
    }

    @Test
    public void queryProductByProductIdTest(){
        System.out.println(productService.queryProductByProductId("pa"));
    }

    @Test
    public void queryProductLikeNameTest(){
        List<Product> productList = productService.queryProductLikeName("产品");
        for(Product p : productList){
            System.out.println(p.toString());
        }
    }
}
