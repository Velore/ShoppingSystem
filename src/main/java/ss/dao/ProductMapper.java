package ss.dao;

import ss.po.Product;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/6
 **/
public interface ProductMapper {

    /**
     * 插入新的商品
     * @param product product
     * @return 是否插入成功
     */
    int insertProduct(Product product);

    /**
     * 更新商品
     * @param product product
     * @return 是否更新成功
     */
    int updateProduct(Product product);

    /**
     * 删除商品
     * @param productId id
     * @return 是否删除成功
     */
    int deleteProduct(String productId);

    /**
     * 查询全部的商品
     * @return product list
     */
    List<Product> queryAllProduct();

    /**
     * 通过id查询商品
     * @param productId id
     * @return product
     */
    Product queryProductByProductId(String productId);

    /**
     * 通过名字模糊查询商品
     * @param productName name
     * @return product list
     */
    List<Product> queryProductLikeName(String productName);
}
