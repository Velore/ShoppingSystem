package ss.service;

import ss.po.Product;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/7
 **/
public interface ProductService {

    /**
     * 插入新的商品
     * @param product p
     * @return 是否插入成功
     */
    boolean insertProduct(Product product);

    /**
     * 更新商品信息
     * @param product p
     * @return 是否更新成功
     */
    boolean updateProduct(Product product);

    /**
     * 删除商品
     * @param productId p
     * @return 是否删除成功
     */
    boolean deleteProduct(String productId);

    /**
     * 查询全部的商品
     * @return product list
     */
    List<Product> queryAllProduct();

    /**
     * 根据商品id查询商品
     * @param productId id
     * @return product
     */
    Product queryProductByProductId(String productId);

    /**
     * 根据名字模糊查询商品
     * @param productName name
     * @return product
     */
    List<Product> queryProductLikeName(String productName);
}
